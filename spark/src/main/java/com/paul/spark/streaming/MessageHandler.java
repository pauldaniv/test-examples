package com.paul.spark.streaming;

import com.paul.spark.model.ConsultationSubmit;
import org.apache.spark.api.java.JavaRDD;

import java.io.Serializable;

public interface MessageHandler extends Serializable {
    void onMessage(JavaRDD<ConsultationSubmit> message);
}
