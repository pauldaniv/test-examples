package com.paul.spark.streaming.init;

import com.paul.spark.streaming.SparkKafkaStreamExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("!test")
public class ApplicationStartup {

    @EventListener(ContextRefreshedEvent.class)
    public void start(ContextRefreshedEvent event) {
        ApplicationContext ac = event.getApplicationContext();
        SparkKafkaStreamExecutor sparkKafkaStreamExecutor = ac.getBean(SparkKafkaStreamExecutor.class);
        Thread thread = new Thread(sparkKafkaStreamExecutor);
        thread.start();
    }
}
