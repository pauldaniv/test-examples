package com.paul.spark.services.streaming.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.paul.spark.model.ConsultationSubmit;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.io.Serializable;
import java.util.function.Supplier;

@Configuration
public class KafkaConfig implements Serializable {
    public static Supplier<KafkaOperations<Object, Object>> kafkaTemplateSupplier;

    @Value("${spring.kafka.template.default-topic}")
    private String defaultTopic;
    @Autowired
    public void kafkaTemplate(KafkaOperations<Object, Object> kafkaTemplate) {
        kafkaTemplateSupplier = () -> kafkaTemplate;
    }

    @Bean
    public RecordMessageConverter converter() {
        return new StringJsonMessageConverter();
    }

    @Autowired
    public  void objectMapper(ObjectMapper objectMapper) {
         objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @KafkaListener(topics = "primary")
    public void primaryListen(ConsultationSubmit in) {
        System.out.println("Received message: " + in);
    }

    @Bean
    public NewTopic defaultTopic() {
        return new NewTopic(defaultTopic, 1, (short) 1);
    }
}
