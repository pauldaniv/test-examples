package com.paul.spark.services;

import com.paul.spark.services.impl.GraphXExampleImpl;

import java.util.List;

public interface GraphXExample {
    List<GraphXExampleImpl.LeaderInfo> mostImportantDepartmentLeaders(int limit);
}
