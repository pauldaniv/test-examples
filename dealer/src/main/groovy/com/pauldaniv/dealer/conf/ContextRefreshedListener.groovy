package com.pauldaniv.dealer.conf

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
@Slf4j
@Profile("!test")
class ContextRefreshedListener {

  @Autowired
  private DbInitializer dbInitializer

  @EventListener(ContextRefreshedEvent)
  void init() {
    log.info("Context Refreshed")
    dbInitializer.init()
  }
}
