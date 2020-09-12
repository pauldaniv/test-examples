package com.pauldaniv.spark.controller;

import com.pauldaniv.spark.model.ConsultationSubmit;
import com.pauldaniv.spark.services.impl.streaming.ProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Message Controller", description = "Used for pushing messages to the kafka queue")
@RestController
@RequestMapping("/api/v1/queue")
@RequiredArgsConstructor
public class MessagePushController {

    private final ProducerService kafkaProducer;


    @Operation(
            summary = "Push message to queue",
            description = "Pushes new 'ticket' about work assign to specific leader",
            tags = "streaming"
    )
    @PostMapping("/{topic}")
    ConsultationSubmit post(
            @Parameter(description = "The topic to be used to push message to, default 'primary'")
            @PathVariable String topic,
            @Parameter(description = "The data that represents a single event of work assigning")
            @RequestBody @Valid ConsultationSubmit data) {
        return kafkaProducer.pushMessage(topic, data);
    }
}
