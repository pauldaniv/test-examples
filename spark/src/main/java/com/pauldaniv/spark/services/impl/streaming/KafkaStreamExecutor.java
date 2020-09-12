package com.pauldaniv.spark.services.impl.streaming;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pauldaniv.spark.model.ConsultationSubmit;
import kafka.serializer.Decoder;
import kafka.serializer.DefaultDecoder;
import kafka.utils.VerifiableProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaStreamExecutor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Value("${spark.stream.kafka.durations}")
    private String streamDurationTime;

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaBootstrapServers;

    private final transient JavaSparkContext javaSparkContext;
    private final MessageHandler messageHandler;

    public void execute() {
        try {
            startStreamTask();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startStreamTask() throws InterruptedException {

        Set<String> topics = new HashSet<>(Collections.singletonList(topic));

        Map<String, String> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", kafkaBootstrapServers);

        JavaStreamingContext jsc = new JavaStreamingContext(javaSparkContext,
                Durations.seconds(Integer.parseInt(streamDurationTime)));
        jsc.checkpoint("build/tmp/checkpoint");

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

    public static class ConsultationSubmitDecoder implements Decoder<ConsultationSubmit> {
        private final Charset encoding;

        @Override
        public ConsultationSubmit fromBytes(byte[] bytes) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return objectMapper.readValue(new String(bytes, encoding), ConsultationSubmit.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public ConsultationSubmitDecoder(VerifiableProperties props) {
            this.encoding = props == null ? UTF_8 : Charset.forName(props.getString("serializer.encoding", UTF_8.name()));
        }
    }
}
