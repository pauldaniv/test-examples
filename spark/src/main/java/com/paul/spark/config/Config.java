package com.paul.spark.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Configuration
public class Config implements Serializable {

    @Value("${spark.app.name}")
    private String appName;
    @Value("${spark.master}")
    private String masterUri;

    @Value("${spark.driver.memory}")
    private String sparkDriverMemory;
    @Value("${spark.worker.memory}")
    private String sparkWorkerMemory;
    @Value("${spark.executor.memory}")
    private String sparkExecutorMemory;
    @Value("${spark.rpc.message.maxSize}")
    private String sparkRpcMessageMaxSize;

    @Bean
    public SparkConf conf() {
        return new SparkConf()
                .setAppName(appName)
                .setMaster(masterUri)
                .set("spark.driver.memory", sparkDriverMemory)
                .set("spark.worker.memory", sparkWorkerMemory)//"26g".set("spark.shuffle.memoryFraction","0") //默认0.2
                .set("spark.executor.memory", sparkExecutorMemory)
                .set("spark.rpc.message.maxSize", sparkRpcMessageMaxSize);
    }

    @Bean
    public JavaSparkContext jsc() {
        return new JavaSparkContext(conf());
    }

    @Bean
    public SQLContext sqlContext(JavaSparkContext context) {
        return new SQLContext(context);
    }
}
