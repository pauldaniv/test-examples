package com.paul.dbservice.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("!test")
public class ContextRefreshedListener {
    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        log.info("Context Refreshed");
    }
}
