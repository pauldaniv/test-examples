package com.paul.spark.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.function.Supplier;

@Configuration
public class Config implements Serializable {
    public static Supplier<JavaSparkContext> javaSparkContextSupplier;
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

    @Value("${cassandra.host}")
    private String cassandraHost;
    @Value("${cassandra.username}")
    private String cassandraUsername;
    @Value("${cassandra.password}")
    private String cassandraPassword;

    @Bean
    public SparkConf conf() {
        return new SparkConf()
                .setAppName(appName)
                .setMaster(masterUri)
                .set("spark.driver.memory", sparkDriverMemory)
                .set("spark.worker.memory", sparkWorkerMemory)
                .set("spark.executor.memory", sparkExecutorMemory)
                .set("spark.rpc.message.maxSize", sparkRpcMessageMaxSize)
                .set("spark.cassandra.connection.host", cassandraHost)
                .set("spark.cassandra.auth.username", cassandraUsername)
                .set("spark.cassandra.auth.password", cassandraPassword);
    }

    @Bean
    public JavaSparkContext jsc() {
        final JavaSparkContext javaSparkContext = new JavaSparkContext(conf());
        javaSparkContextSupplier = () -> javaSparkContext;
        return javaSparkContext;
    }

    @Bean
    public SQLContext sqlContext(JavaSparkContext context) {
        return new SQLContext(context);
    }
}
