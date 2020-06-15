package com.paul.spark.services;

import java.io.Serializable;

public interface MessageService extends Serializable {
    <T> T pushMessage(String topic, T data);
}
