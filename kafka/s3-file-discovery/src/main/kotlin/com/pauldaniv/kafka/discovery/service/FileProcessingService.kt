package com.pauldaniv.kafka.discovery.service

interface FileProcessingService {
  fun processFiles(bucket: String, customValue: String?)
}
