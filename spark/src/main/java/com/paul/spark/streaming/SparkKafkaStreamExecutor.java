package com.paul.spark.streaming;

import com.paul.spark.model.ConsultationSubmit;
import kafka.serializer.DefaultDecoder;
import lombok.RequiredArgsConstructor;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SparkKafkaStreamExecutor implements Serializable, Runnable {

    private static final long serialVersionUID = 1L;

    @Value("${spark.stream.kafka.durations}")
    private String streamDurationTime;

    @Value("${spring.kafka.template.default-topic}")
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

        Map<String, String> kafkaParams = new HashMap<>();
        kafkaParams.put("metadata.broker.list", "localhost:9092");

        JavaStreamingContext jsc = new JavaStreamingContext(javaSparkContext,
                Durations.seconds(Integer.parseInt(streamDurationTime)));
        jsc.checkpoint("build/checkpoint");

        final JavaPairInputDStream<byte[], ConsultationSubmit> stream = KafkaUtils.createDirectStream(
                jsc,
                byte[].class,
                ConsultationSubmit.class,
                DefaultDecoder.class,
                ConsultationSubmitDecoder.class,
                kafkaParams,
                topics
        );

        stream.foreachRDD(message -> messageHandler.onMessage(message.values()));

        jsc.start();
        jsc.awaitTermination();
    }
}
