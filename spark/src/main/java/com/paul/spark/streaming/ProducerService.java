package com.paul.spark.streaming;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProducerService implements Serializable {

    private transient final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "primary";

    public void sendMessage(final String message) {
        log.info(String.format("#### -> Producing message -> %s", message));
        this.kafkaTemplate.send(TOPIC, message);
    }

    public void pushForward(final String message) {
        this.kafkaTemplate.send("next", message);
    }
}
