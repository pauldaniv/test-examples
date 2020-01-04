package com.paul.spark.dataset;

import com.paul.spark.model.Person;
import lombok.RequiredArgsConstructor;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SQLContext;
import org.springframework.stereotype.Service;
import scala.Tuple2;

@Service
@RequiredArgsConstructor
public class DatasetExampleImpl implements DatasetExample {

    private final SQLContext sqlContext;

    @Override
    public Long count(String fileName) {
        return getDataset(fileName).count();
    }

    private JavaRDD<String> getDataset(String fileName) {
        return sqlContext.read().textFile("spark/data/" + fileName).toJavaRDD();

//        final JavaRDD<String> stringJavaRDD = sparkContext.textFile("people.txt");
//        return stringJavaRDD
//                .map(it -> it.split(","))
//                .map(it -> Tuple2.apply(it[0].trim(), Integer.valueOf(it[1].trim())))
//                .map(it -> new Person(it._1, it._2))
//                .rdd().toJavaRDD();
    }
}
