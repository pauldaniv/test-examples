package com.pauldaniv.kafka.discovery.service

import com.pauldaniv.kafka.common.model.Bar

interface FileProducerService {

  fun sendFoos(what: String?)

  fun sendS3Objects(objectKeys: List<Bar>)
}
