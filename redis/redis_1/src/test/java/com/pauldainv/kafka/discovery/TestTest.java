//package com.pauldainv.kafka.discovery.tx;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.kafka.test.assertj.KafkaConditions.key;
//import static org.springframework.kafka.test.assertj.KafkaConditions.value;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pauldaniv.kafka.common.model.Bar;
//import com.pauldaniv.kafka.common.model.Foo;
//import com.pauldaniv.kafka.discovery.tx.service.S3ObjectProducerService;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.ClassRule;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.listener.ContainerProperties;
//import org.springframework.kafka.listener.KafkaMessageListenerContainer;
//import org.springframework.kafka.listener.MessageListener;
//import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
//import org.springframework.kafka.test.utils.ContainerTestUtils;
//import org.springframework.kafka.test.utils.KafkaTestUtils;
//
//import java.io.IOException;
//import java.util.Collections;
//import java.util.Map;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.TimeUnit;
//
//@SpringBootTest
//public class TestTest {
//    private static final Logger LOGGER = LoggerFactory.getLogger(TestTest.class);
//
//    private static String TOPIC_NAME = "topic2";
//    @ClassRule
//    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, TOPIC_NAME);
//    @Autowired
//    private S3ObjectProducerService s3ObjectProducerService;
//    private KafkaMessageListenerContainer<String, Foo> container;
//    private BlockingQueue<ConsumerRecord<String, String>> consumerRecords;
//
//    @Before
//    public void setUp() {
//        consumerRecords = new LinkedBlockingQueue<>();
//
//        ContainerProperties containerProperties = new ContainerProperties(TOPIC_NAME);
//
//        Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps(
//                "sender", "false", embeddedKafka.getEmbeddedKafka());
//
//        DefaultKafkaConsumerFactory<String, Foo> consumer = new DefaultKafkaConsumerFactory<>(consumerProperties);
//
//        container = new KafkaMessageListenerContainer<>(consumer, containerProperties);
//        container.setupMessageListener((MessageListener<String, String>) record -> {
//            LOGGER.debug("Listened message='{}'", record.toString());
//            consumerRecords.add(record);
//        });
//        container.start();
//
//        ContainerTestUtils.waitForAssignment(container, embeddedKafka.getEmbeddedKafka().getPartitionsPerTopic());
//    }
//
//    @After
//    public void tearDown() {
//        container.stop();
//    }
//
//    @Test
//    public void testSendMessage() throws InterruptedException, IOException {
//        final Bar bar = new Bar("key", "hello");
//        s3ObjectProducerService.sendS3Objects(Collections.singletonList(bar));
//
//        ConsumerRecord<String, String> received = consumerRecords.poll(10, TimeUnit.SECONDS);
//
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(bar);
//
//        assertThat(received).has(value(json));
//
//        assertThat(received).has(key(null));
//    }
//
//}
