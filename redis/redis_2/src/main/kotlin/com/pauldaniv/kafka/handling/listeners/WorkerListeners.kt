package com.pauldaniv.kafka.handling.listeners

import com.pauldaniv.kafka.common.model.Bar
import com.pauldaniv.kafka.common.model.Foo
import com.pauldaniv.kafka.handling.service.S3ObjectInfoService
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class WorkerListeners(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    private val s3ObjectInfoService: S3ObjectInfoService,
) {

  private val log = LoggerFactory.getLogger(this::class.java)

  // here we are able to successfully receive list of Bars only
  // because we have 'batch' listener mode enabled otherwise
  // (if we send lists of Bars explicitly) we'll receive it as
  // list of Maps (what?) yes, that's right there might be other
  // way doing that (should be, you dumass!). Possibly by
  // configuring message converter map or smth
  @KafkaListener(groupId = "handling", topics = ["topic2"])
  fun listen1(bar: Bar) {
    val bars = listOf(bar)
    log.info("Received: $bars")

    // and yes, it should also work the other way around, if we
    // send messages here like list it 'supposed' to break the
    // listener on the other end. So better to iterate through
    // and send it one by one. Crap!
    bars.forEach { kafkaTemplate.send("received", Foo("received", it.bar)) }

    val s3Objects = bars.map { Foo(it.key, it.bar) }
    s3ObjectInfoService.storeObjects(s3Objects)
    log.info("Persisted: $bars")

    bars.forEach { kafkaTemplate.send("persisted", Foo("persisted", it.bar)) }

    // this is hell of a weird, but we have what we have
    // this supposed to be at the beginning of the processing :)
    // so that we would remove or 'validate' bad entries, maybe
    // filter them out, and then process. But we want to simulate
    // runtime error that happened on multilevel pipeline.
    // And, given it's happening inside transaction it then
    // 'supposed' to be rolled back
    // And yet again, given there is no quick way (AFAIK) to get
    // it working meaning it should remove messages from the queue
    // but doing it in such a way that the transaction doesn't commit
    // invalid message, we doing a trick that will put the invalid batch
    // into another queue. It still means that the bad transaction will happen
    // but at least we'll have track of invalid batch somewhere,

    if (bars.find { it.bar.contains("fail1") } != null) {
      kafkaTemplate.send("errorHandleTopic", "aoeu" )
    }
    if (bars.find { it.bar.contains("fail") } != null) {
      // normally, I'd just throw an exception here, but since there is no obvious
      // or 'easy' way to handle it the way I want, I'll just push an 'error' event
      // to a 'errorHandleTopic' so that it'll be tracked
      // This would work perfectly fine if we have a 'simple' message, that let's say
      // has only S3 key, or something similar, and more importantly, can be postponed
      // to process, like populate some metadata around the file. (I think it's called
      // idempotent messages, probably yeah). BUT, if it's not e.i, the message has
      // complex structure and it's meaning is relevant within specific timeframe, then we're doomed!!, seriously

      // and of course the only problem here is that it sends it under transaction too :(
      kafkaTemplate.send("errorHandleTopic", "A message batch contained 'fail' word...\nI guess I must fail now")
      throw IllegalStateException("I must to fail!")
    }

    kafkaTemplate.send("topic3", bars.map { it.bar })
    bars.forEach { kafkaTemplate.send("transferred", Foo("transferred", it.bar)) }

    log.info("Messages sent, hit Enter to commit tx")

    println()
  }

  @KafkaListener(groupId = "handling", topics = ["topic3"])
  fun listen2(`in`: String) {
    log.info("Received: $`in`")
  }
}
