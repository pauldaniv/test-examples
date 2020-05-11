package com.paul.spark.streaming;

import static java.util.Comparator.naturalOrder;
import static java.util.stream.StreamSupport.stream;
import static scala.Tuple2.apply;

import com.paul.spark.graphx.GraphXExample;
import com.paul.spark.model.ConsultationSubmit;
import com.paul.spark.model.Statistic;
import lombok.RequiredArgsConstructor;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.springframework.stereotype.Service;
import scala.Tuple2;
import scala.Tuple3;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageHandlerImpl implements MessageHandler {

    private final GraphXExample graphXExample;
    private final StatisticStorage storage;
    private final ProducerService producerService;

    @Override
    public void onMessage(final JavaRDD<ConsultationSubmit> message) {
        if (message.isEmpty()) return;

        final JavaPairRDD<String, Tuple2<String, Long>> auraData = graphXExample.getGraphRDD().cache();

        final JavaPairRDD<String, Tuple3<String, Long, LocalDateTime>> byLatestDate = message
                .mapToPair(it -> apply(apply(it.getUserId(), it.getConsultationId()), it.getSubmittedAt()))
                .groupByKey()
                .mapToPair(it -> apply(
                        it._1._1,
                        Tuple3.apply(it._1._2, it._2.spliterator().estimateSize(),
                                stream(it._2.spliterator(), false).max(naturalOrder()).get())));

        final JavaPairRDD<String, Tuple2<Tuple2<String, Long>, Tuple3<String, Long, LocalDateTime>>> joined =
                auraData.join(byLatestDate);

        if (joined.isEmpty()) return;
        producerService.pushForward(storage.store(joined).collect());
    }
}
