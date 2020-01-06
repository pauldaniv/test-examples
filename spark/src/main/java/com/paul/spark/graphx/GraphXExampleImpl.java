package com.paul.spark.graphx;

import com.paul.spark.dataframe.DataFrameExample;
import com.paul.spark.model.Engagement;
import lombok.RequiredArgsConstructor;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.graphx.Edge;
import org.apache.spark.graphx.Graph;
import org.springframework.stereotype.Service;
import scala.reflect.ClassTag;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.spark.storage.StorageLevel.MEMORY_ONLY;

@Service
@RequiredArgsConstructor
public class GraphXExampleImpl implements GraphXExample {

    private final JavaSparkContext sc;
    private final DataFrameExample dataFrameExample;

    @Override
    public void getGraph() {
        final List<Engagement> engagements = dataFrameExample.collectAuraData();

        final List<Edge<String>> edgesList = engagements.stream()
                .map(this::getEdges)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        final JavaRDD<Edge<String>> parallelize = sc.parallelize(edgesList);

        ClassTag<String> stringTag = scala.reflect.ClassTag$.MODULE$.apply(String.class);

        Graph<String, String> graph = Graph.fromEdges(
                parallelize.rdd(),
                "nothing",
                MEMORY_ONLY(),
                MEMORY_ONLY(),
                stringTag,
                stringTag
        );

        graph.vertices().toJavaRDD().collect().forEach(System.out::println);

    }

    private List<Edge<String>> getEdges(final Engagement engagement) {
        return engagement.getLeaders()
                .stream().flatMap(leader -> engagement.getMembers().stream()
                        .map(member -> new Edge<>(
                                leader.getGuid().hashCode(),
                                member.getGuid().hashCode(),
                                engagement.getGuid()
                        )))
                .collect(Collectors.toList());
    }

    private static class Relationship {
        private String engagementGUID;
        private RelationshipType first;
        private RelationshipType second;
    }

    private enum RelationshipType {
        MANAGER, LEADER, MEMBER;
    }
}
