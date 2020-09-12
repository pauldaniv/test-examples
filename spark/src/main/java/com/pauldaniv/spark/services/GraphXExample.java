package com.pauldaniv.spark.services;

import com.pauldaniv.spark.services.impl.GraphXExampleImpl;

import java.util.List;

public interface GraphXExample {
    List<GraphXExampleImpl.LeaderInfo> mostImportantDepartmentLeaders(int limit);
}
