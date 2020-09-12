package com.pauldaniv.kafkaservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProducerService {
    @SuppressWarnings("unchecked")
    public <T> T pushMessage(String topic, T data) {
        log.info("Pushing message {} to topic {}", data, topic);
        try {
            return (T) KafkaConfig.kafkaTemplateSupplier
                    .get().send(topic, data)
                    .get().getProducerRecord()
                    .value();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
