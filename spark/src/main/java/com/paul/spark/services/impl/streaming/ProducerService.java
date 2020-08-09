package com.paul.spark.services.impl.streaming;

import com.paul.spark.config.KafkaConfig;
import com.paul.spark.services.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProducerService implements MessageService {
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
