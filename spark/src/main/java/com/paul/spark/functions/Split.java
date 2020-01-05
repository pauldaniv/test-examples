package com.paul.spark.functions;

import org.apache.spark.sql.api.java.UDF1;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Split implements UDF1<String, List<String>> {

    public String getName() {
        return "split";
    }

    @Override
    public List<String> call(String s) throws Exception {
        return Arrays.asList(s.split(","));
    }
}
