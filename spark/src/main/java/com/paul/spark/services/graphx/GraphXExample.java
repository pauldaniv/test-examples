package com.paul.spark.services.graphx;

import java.util.List;

public interface GraphXExample {
    List<GraphXExampleImpl.EngagementView> mostImportantEngagementLeaders(int limit);
}
