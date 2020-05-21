package com.paul.spark.services.streaming;

import com.paul.spark.model.ConsultationSubmit;
import com.paul.spark.services.streaming.init.KafkaConfig;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/queue")
public class Controller {
    @RequestMapping("/{topic}")
    Object post(@PathVariable String topic, @RequestBody ConsultationSubmit data) throws Exception{
        return  KafkaConfig.kafkaTemplateSupplier.get().send(topic, data).get().getProducerRecord().value();
    }
}
