package com.pauldaniv.spark.config;

import com.pauldaniv.spark.services.impl.streaming.KafkaStreamExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("!test")
@RequiredArgsConstructor
public class ApplicationStartup {

    private final KafkaStreamExecutor sparkKafkaStreamExecutor;

    @EventListener(ContextRefreshedEvent.class)
    public void start() {
        Thread thread = new Thread(sparkKafkaStreamExecutor::execute);
        thread.start();
    }
}
