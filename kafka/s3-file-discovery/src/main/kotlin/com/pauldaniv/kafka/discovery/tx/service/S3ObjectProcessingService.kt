package com.pauldaniv.kafka.discovery.tx.service

import com.pauldaniv.kafka.common.model.Foo1
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response

@Component
class S3ObjectProcessingService(
    private val s3Client: S3Client,
    private val s3ObjectProducerService: S3ObjectProducerService
) {

  fun processS3Objets(bucket: String) {
    var listObjects: ListObjectsV2Request? = ListObjectsV2Request.builder()
        .bucket(bucket)
        .maxKeys(100)
        .build()

    do {
      val res: ListObjectsV2Response = s3Client.listObjectsV2(listObjects)
      if (res.hasContents()) {
        val objectKeys = res.contents()
            .map { it.key() }
            .filter { it.matches(".*/[0-9a-f]{64}".toRegex()) }
            .map { Foo1("key", it.substringAfterLast("/")) }

        s3ObjectProducerService.sendS3Objects(objectKeys)
      }

      listObjects = ListObjectsV2Request.builder()
          .bucket(bucket)
          .continuationToken(res.nextContinuationToken())
          .maxKeys(2)
          .build()
    } while (res.nextContinuationToken() != null)
  }
}
