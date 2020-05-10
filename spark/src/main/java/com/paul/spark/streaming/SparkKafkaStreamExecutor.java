package com.paul.spark.streaming;

import static org.apache.spark.streaming.kafka010.KafkaUtils.createDirectStream;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import scala.Tuple2;
import scala.Tuple3;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//import kafka.serializer.StringDecoder;
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.streaming.api.java.JavaPairInputDStream;

@Component
@RequiredArgsConstructor
public class SparkKafkaStreamExecutor implements Serializable, Runnable {

    private static final long serialVersionUID = 1L;

    @Value("${spark.stream.kafka.durations}")
    private String streamDurationTime;

    @Value("${kafka.broker.list}")
    private String metadatabrokerlist;

    @Value("${spark.kafka.topic}")
    private String topic;

    private final transient JavaSparkContext javaSparkContext;
    private final MessageHandler messageHandler;

    @Override
    public void run() {
        try {
            startStreamTask();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startStreamTask() throws InterruptedException {

        Set<String> topics = new HashSet<>(Collections.singletonList(topic));

        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("metadata.broker.list", metadatabrokerlist);
        kafkaParams.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaParams.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        JavaStreamingContext jsc = new JavaStreamingContext(javaSparkContext,
                Durations.seconds(Integer.parseInt(streamDurationTime)));
        jsc.checkpoint("checkpoint");
        JavaInputDStream<ConsumerRecord<String, String>> directStream = createDirectStream(
                jsc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topics, kafkaParams)
        );
//        final JavaPairInputDStream<String, String> stream = createDirectStream(
//                jsc,
//                String.class,
//                String.class,
//                StringDecoder.class,
//                StringDecoder.class,
//                kafkaParams,
//                topics
//        );

        directStream.foreachRDD(message -> messageHandler.onMessage(composeConsultationEvent(message)));

        jsc.start();
        jsc.awaitTermination();
    }

    private JavaRDD<Tuple3<String, String, String>> composeConsultationEvent(JavaRDD<ConsumerRecord<String, String>> raw) {
        return raw.mapToPair(it -> new Tuple2<>(it.key(), it.value()))
                .values()
                .filter(it -> it.contains(","))
                .map(it -> it.split(","))
                .filter(it -> it.length > 2)
                .map(it -> Tuple3.apply(it[0].trim(), it[1].trim(), it[2].trim()));
    }
}
