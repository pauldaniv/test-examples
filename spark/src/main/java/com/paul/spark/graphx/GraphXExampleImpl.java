package com.paul.spark.graphx;

import com.paul.spark.dataframe.DataFrameExample;
import com.paul.spark.model.Engagement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.graphx.Edge;
import org.apache.spark.graphx.Graph;
import org.apache.spark.graphx.lib.PageRank;
import org.springframework.stereotype.Service;
import scala.Tuple2;
import scala.reflect.ClassTag;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.spark.storage.StorageLevel.MEMORY_ONLY;
import static scala.Tuple2.apply;

@Service
@RequiredArgsConstructor
public class GraphXExampleImpl implements GraphXExample, Serializable {

    private transient final JavaSparkContext sc;
    private final DataFrameExample dataFrameExample;

    @Override
    public JavaPairRDD<String, Tuple2<String, Long>> getGraphRDD() {
        final JavaRDD<Engagement> engagementJavaRDD = sc
                .parallelize(dataFrameExample.collectAuraData());

        final JavaRDD<Edge<EngagementMeta>> graphRDD = engagementJavaRDD
                .map(this::getEdges)
                .flatMap(List::iterator);

        ClassTag<EngagementMeta> classTag = scala.reflect.ClassTag$.MODULE$.apply(EngagementMeta.class);

        final JavaPairRDD<Long, EngagementMeta> engagements = graphRDD
                .mapToPair(it -> apply(it.srcId(), it.attr()));

        final Graph<EngagementMeta, EngagementMeta> engagementGraph = Graph.fromEdges(
                graphRDD.rdd(),
                new EngagementMeta(),
                MEMORY_ONLY(),
                MEMORY_ONLY(),
                classTag,
                classTag
        );

        final Graph<Object, Object> rankPage = PageRank.run(engagementGraph, 15, 0.001, classTag, classTag);

        final JavaPairRDD<Long, Double> rank = rankPage
                .vertices()
                .toJavaRDD()
                .mapToPair((PairFunction<Tuple2<Object, Object>, Long, Double>) it ->
                        new Tuple2<>((Long) it._1(), (Double) it._2()));

        final JavaPairRDD<Long, Tuple2<Double, EngagementMeta>> joined = rank.join(engagements);

        return joined
                .distinct()
                .mapToPair(it -> apply(it._2, it._2._2))
                .mapToPair(it -> apply(apply(it._2().userId, it._2().userName), 1L))
                .reduceByKey(Long::sum)
                .mapToPair(it -> apply(it._1._1, apply(it._1._2, it._2)))
                .sortByKey(false);
    }

    @Override
    public List<Tuple2<String, Tuple2<String, Long>>> getGraphResult(int limit) {
        return getGraphRDD()
                .take(limit);
    }

    private List<Edge<EngagementMeta>> getEdges(final Engagement engagement) {
        return engagement.getLeaders()
                .stream()
                .filter(it -> !it.getGuid().equalsIgnoreCase("null"))
                .flatMap(leader -> engagement.getMembers()
                        .stream()
                        .filter(it -> !it.getGuid().equalsIgnoreCase("null"))
                        .map(member -> new Edge<>(
                                leader.getGuid().hashCode(),
                                member.getGuid().hashCode(),
                                new EngagementMeta(leader.getGuid(), leader.getFullName(), engagement.getName())
                        )))
                .collect(Collectors.toList());
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    private static class EngagementMeta implements Serializable {
        private String userId;
        private String userName;
        private String engagementName;
    }
}
