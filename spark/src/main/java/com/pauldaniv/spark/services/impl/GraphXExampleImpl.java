package com.pauldaniv.spark.services.impl;

import static org.apache.spark.storage.StorageLevel.MEMORY_ONLY;
import static scala.Tuple2.apply;
import static scala.reflect.ClassTag$.MODULE$;

import com.pauldaniv.spark.model.Engagement;
import com.pauldaniv.spark.model.User;
import com.pauldaniv.spark.services.DataFrameExample;
import com.pauldaniv.spark.services.GraphXExample;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GraphXExampleImpl implements GraphXExample, Serializable {

    private static final ClassTag<DepartmentMeta> CLASS_TAG = MODULE$.apply(DepartmentMeta.class);
    private final DataFrameExample dataFrameExample;
    private transient final JavaSparkContext sc;

    @Override
    public List<LeaderInfo> mostImportantDepartmentLeaders(int limit) {
        JavaPairRDD<Long, Tuple2<Double, DepartmentMeta>> engagementsRank = rankByEngagements();
        return engagementsRank
                .mapToPair(it -> apply(it._2._1, it._2._2))
                .sortByKey(false)
                .map(it -> LeaderInfo.builder().rank(it._1).userId(it._2.userId).userName(it._2.userName).build())
                .take(limit);
    }

    private JavaPairRDD<Long, Tuple2<Double, DepartmentMeta>> rankByEngagements() {
        final JavaRDD<Edge<DepartmentMeta>> edges = sc
                .parallelize(dataFrameExample.collectAuraData())
                .map(this::getEdges)
                .flatMap(List::iterator)
                .distinct()
                .cache();

        final JavaPairRDD<Long, DepartmentMeta> engagements = edges
                .mapToPair(it -> apply(it.srcId(), it.attr()));

        // do the page rank here
        final Graph<Object, Object> pageRank = PageRank.run(
                getEngagementGraph(edges),
                10,
                0.0001,
                CLASS_TAG, CLASS_TAG
        );

        // ranked engagement (srt, rank) leaders by they "importance"
        final JavaPairRDD<Long, Double> rank = pageRank
                .vertices()
                .toJavaRDD()
                .mapToPair((PairFunction<Tuple2<Object, Object>, Long, Double>) it ->
                        apply((Long) it._1(), (Double) it._2()));

        // join so we can know the engagement leaders we just ranked
        return rank.join(engagements);
    }

    private Graph<DepartmentMeta, DepartmentMeta> getEngagementGraph(JavaRDD<Edge<DepartmentMeta>> graphRDD) {
        return Graph.fromEdges(
                graphRDD.rdd(),
                new DepartmentMeta(),
                MEMORY_ONLY(),
                MEMORY_ONLY(),
                CLASS_TAG,
                CLASS_TAG
        );
    }

    /**
     * Map engagement leaders to edges
     * Each engagement id and all it's members are turned into edges
     * engId (src) -> eachMemberId (dst)
     */
    private List<Edge<DepartmentMeta>> getEdges(final Engagement engagement) {
        return engagement.getLeaders()
                .stream()
                .filter(notNull())
                .filter(it -> engagement.getMembers().stream().noneMatch(m -> m.getGuid().equals(it.getGuid())))
                .flatMap(leader -> engagement.getMembers()
                        .stream()
                        .filter(notNull())
                        .filter(it -> engagement.getLeaders().stream().noneMatch(l -> l.getGuid().equals(it.getGuid())))
                        .map(member -> new Edge<>(
                                member.getGuid().hashCode(),
                                leader.getGuid().hashCode(),
                                new DepartmentMeta(
                                        leader.getGuid(),
                                        leader.getFullName(),
                                        engagement.getName()
                                )
                        )))
                .collect(Collectors.toList());
    }

    private Predicate<User> notNull() {
        return it -> !it.getGuid().equalsIgnoreCase("null");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    private static class DepartmentMeta implements Serializable {
        private String userId;
        private String userName;
        private String engagementName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    @Builder
    public static class LeaderInfo implements Serializable {
        private String userName;
        private String userId;
        private Double rank;
    }
}
