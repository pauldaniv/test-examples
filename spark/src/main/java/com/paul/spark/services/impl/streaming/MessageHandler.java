package com.paul.spark.services.impl.streaming;

import static java.util.Comparator.naturalOrder;
import static java.util.stream.StreamSupport.stream;
import static scala.Tuple2.apply;

import com.paul.spark.model.ConsultationSubmit;
import com.paul.spark.model.Engagement;
import com.paul.spark.services.DataFrameExample;
import lombok.RequiredArgsConstructor;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.stereotype.Component;
import scala.Tuple2;
import scala.Tuple3;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MessageHandler implements Serializable {
    private static final String TOPIC = "primary";

    private transient final JavaSparkContext sc;
    private final DataFrameExample dataFrameExample;

    private final StatisticPersistence<String, Tuple3<String, Long, LocalDateTime>> storage;
    private final ProducerService producerService;

    public void onMessage(final JavaRDD<ConsultationSubmit> message) {
        if (message.isEmpty()) return;

        final JavaPairRDD<String, Engagement> auraData = sc
                .parallelize(dataFrameExample.collectAuraData())
                .map(it -> it.getLeaders().stream().map(l -> apply(l.getGuid(), it)).collect(Collectors.toList()))
                .flatMapToPair(List::iterator)
                .distinct()
                .cache();


        final JavaPairRDD<Tuple2<String, String>, LocalDateTime> messageRdd = message
                .mapToPair(it -> apply(apply(it.getUserId(), it.getDepartmentId()), it.getSubmittedAt())).cache();

        JavaPairRDD<String, String> engagementsById = auraData.mapToPair(it -> apply(it._2.getGuid(), it._2.getName()));

        JavaPairRDD<String, String> stringStringJavaPairRDD = messageRdd.mapToPair(it -> apply(it._1._2, it._1._1));
        JavaPairRDD<String, Tuple2<String, String>> engagementsNamesAndUsersById
                = stringStringJavaPairRDD.join(engagementsById);

        JavaPairRDD<String, Tuple2<String, String>> usersEngagementsNameAndEngagementIdByUserId
                = engagementsNamesAndUsersById.mapToPair(it -> apply(it._2._1, apply(it._1, it._2._2)));

        final JavaPairRDD<String, Tuple3<String, Long, LocalDateTime>> byLatestDate = messageRdd
                .groupByKey()
                .mapToPair(it -> apply(
                        it._1._1,
                        Tuple3.apply(it._1._2, it._2.spliterator().estimateSize(),
                                stream(it._2.spliterator(), false).max(naturalOrder()).orElse(null))));


        JavaPairRDD<String, Tuple3<String, Long, LocalDateTime>> joined
                = byLatestDate.join(usersEngagementsNameAndEngagementIdByUserId)
                .mapToPair(it -> apply(it._1, Tuple3.apply(it._2._2._1, it._2._1._2(), it._2._1._3())))
                //TODO find the cause and fix
                .distinct();

        if (joined.isEmpty()) return;
        producerService.pushMessage(TOPIC, storage.store(joined).collect());
    }
}
