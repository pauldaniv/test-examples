package com.pauldaniv.kafka.controller

import com.pauldaniv.kafka.common.Foo1
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.kafka.core.KafkaOperations
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response
import software.amazon.awssdk.services.s3.model.S3Object

@Tag(name = "Message Controller", description = "Used for pushing messages to the kafka queue")
@RestController
@RequestMapping("/tx")
class TransactionsController(private val template: KafkaTemplate<Any?, Any?>, private val s3Client: S3Client) {

  @PostMapping("/send/foos/{what}")
  fun sendFoo(@PathVariable what: String?) {
    template.executeInTransaction<Any?> { kafkaTemplate: KafkaOperations<Any?, in Any?> ->
      StringUtils.commaDelimitedListToSet(what).stream()
          .map { s: String? -> Foo1(s) }
          .forEach { foo: Foo1? -> kafkaTemplate.send("topic2", foo) }
      Foo1("done")
    }
  }

  @PostMapping("/s3-discovery/{bucket}")
  fun listS3(@PathVariable bucket: String) {
    var listObjects: ListObjectsV2Request? = ListObjectsV2Request.builder()
        .bucket(bucket)
        .maxKeys(100)
        .build()

    template.executeInTransaction<Any?> { kafkaTemplate: KafkaOperations<Any?, in Any?> ->
      do {
        val res: ListObjectsV2Response = s3Client.listObjectsV2(listObjects)
        if (res.hasContents()) {
          val objects: List<S3Object> = res.contents()

          val iterVals: ListIterator<*> = objects.listIterator()
          while (iterVals.hasNext()) {
            val s3Object = iterVals.next() as S3Object
            if (s3Object.key().matches(".*/[0-9a-f]{64}".toRegex())) {
              kafkaTemplate.send("topic2", Foo1(s3Object.key().substringAfterLast("/")))
            }
          }
        }
        listObjects = ListObjectsV2Request.builder()
            .bucket("fym-memes")
            .continuationToken(res.nextContinuationToken())
            .maxKeys(2)
            .build()
      } while (res.nextContinuationToken() != null)
    }
  }
}
