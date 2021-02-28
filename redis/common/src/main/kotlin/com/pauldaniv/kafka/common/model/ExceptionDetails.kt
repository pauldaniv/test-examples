package com.pauldaniv.kafka.common.model

data class ExceptionDetails(val sourceTopic: String, val data: Any, val exception: Exception? = null)
