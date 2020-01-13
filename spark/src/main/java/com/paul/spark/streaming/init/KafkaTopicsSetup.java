package com.paul.spark.streaming.init;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//@Component
@Slf4j
@Profile("!test")
public class KafkaTopicsSetup {

    @Value("${kafka.broker.list}")
    private String bootstrapServer;

//    @EventListener(ContextRefreshedEvent.class)
    public void init() throws IOException {
        log.info("Context Refreshed");

        Properties properties = new Properties();
        properties.load(new FileReader(new File("kafka.properties")));
        properties.setProperty("bootstrap.servers", bootstrapServer);

        AdminClient adminClient = AdminClient.create(properties);
        NewTopic newTopic = new NewTopic("topicName", 1, (short) 1);

        List<NewTopic> newTopics = new ArrayList<>();
        newTopics.add(newTopic);

        adminClient.createTopics(newTopics);
        adminClient.close();
    }
}
