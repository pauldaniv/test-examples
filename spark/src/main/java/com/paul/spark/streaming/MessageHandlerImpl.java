package com.paul.spark.streaming;

import com.paul.spark.graphx.GraphXExample;
import com.paul.spark.model.Statistic;
import kafka.utils.Json;
import lombok.RequiredArgsConstructor;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.springframework.stereotype.Service;
import scala.Tuple2;
import scala.Tuple3;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Comparator.naturalOrder;
import static java.util.stream.StreamSupport.stream;
import static scala.Tuple2.apply;

@Service
@RequiredArgsConstructor
public class MessageHandlerImpl implements MessageHandler {

    private final GraphXExample graphXExample;
    private final StatisticStorage storage;
    private final ProducerService producerService;
    @Override
    public void onMessage(final JavaRDD<Tuple3<String, String, String>> message) {
        if (message.isEmpty()) return;

        final JavaPairRDD<String, Tuple2<String, Long>> auraData = graphXExample.getGraphRDD().cache();

        final JavaPairRDD<String, Tuple3<String, Long, LocalDateTime>> byLatestDate = message
                .mapToPair(it -> apply(apply(it._1(), it._2()), parse(it._3(),
                        ofPattern("yyyy-MM-dd HH:mm:ss"))))
                .groupByKey()
                .mapToPair(it -> apply(
                        it._1._1,
                        Tuple3.apply(it._1._2, it._2.spliterator().estimateSize(),
                                stream(it._2.spliterator(), false).max(naturalOrder()).get())));

        final JavaPairRDD<String, Tuple2<Tuple2<String, Long>, Tuple3<String, Long, LocalDateTime>>> joined =
                auraData.join(byLatestDate);


        final JavaRDD<Statistic> statistic = storage.store(joined);

        statistic.foreach(it -> producerService.pushForward(Json.encode(it)));
    }
}
