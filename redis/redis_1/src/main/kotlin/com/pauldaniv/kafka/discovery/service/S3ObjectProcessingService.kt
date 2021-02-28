package com.pauldaniv.kafka.discovery.service

import com.pauldaniv.kafka.common.model.Bar
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response

@Service
class S3ObjectProcessingService(
    private val s3Client: S3Client,
    private val s3ObjectProducerService: S3ObjectProducerService
) : FileProcessingService {

  override fun processFiles(bucket: String, customValue: String) {
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
            .map { Bar("key", it.substringAfterLast("/")) }.toMutableList()
        customValue?.let { objectKeys.add(Bar("key", it)) }
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
