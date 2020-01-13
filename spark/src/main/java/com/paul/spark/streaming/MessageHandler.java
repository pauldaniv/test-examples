package com.paul.spark.streaming;


import org.apache.spark.api.java.JavaRDD;
import scala.Tuple3;

import java.io.Serializable;

public interface MessageHandler extends Serializable {
    void onMessage(JavaRDD<Tuple3<String, String, String>> message);
}
