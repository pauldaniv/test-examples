package com.paul.spark.services.streaming;

import com.paul.spark.model.Statistic;
import com.paul.spark.services.streaming.init.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProducerService implements Serializable {

    private static final String TOPIC = "primary";

    public void sendMessage(final Statistic message) {
        log.info(String.format("#### -> Producing message -> %s", message));
        KafkaConfig.kafkaTemplateSupplier.get().send(TOPIC, null, message);
    }

    public void pushForward(final List<Statistic> message) {
        KafkaConfig.kafkaTemplateSupplier.get().send("next", message);
    }
}
