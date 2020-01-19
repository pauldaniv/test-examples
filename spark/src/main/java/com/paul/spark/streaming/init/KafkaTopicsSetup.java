package com.paul.spark.streaming.init;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

//@Component
@Slf4j
@Profile("!test")
public class KafkaTopicsSetup {

    @Value("${kafka.broker.list}")
    private String bootstrapServer;
    @Value("${spark.kafka.topic}")
    private String topic;

    @EventListener(ContextRefreshedEvent.class)
    public void init() throws IOException, ExecutionException, InterruptedException {
        log.info("Context Refreshed");

        Properties properties = new Properties();
        properties.load(new FileReader(new File("kafka.properties")));
        properties.setProperty("bootstrap.servers", bootstrapServer);

        AdminClient adminClient = AdminClient.create(properties);

        if (adminClient.listTopics().names().get().stream().noneMatch(it -> it.equals(topic))) {
            NewTopic newTopic = new NewTopic("topic", 1, (short) 1);

            List<NewTopic> newTopics = new ArrayList<>();
            newTopics.add(newTopic);
            adminClient.createTopics(newTopics);
        }
        adminClient.close();
    }
}
