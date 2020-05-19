package com.paul.spark.graphx;

import static org.apache.spark.storage.StorageLevel.MEMORY_ONLY;
import static scala.Tuple2.apply;
import static scala.reflect.ClassTag$.MODULE$;

import com.paul.spark.dataframe.DataFrameExample;
import com.paul.spark.model.Engagement;
import com.paul.spark.model.User;
import lombok.AllArgsConstructor;
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
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class GraphXExampleImpl implements GraphXExample, Serializable {

    private static final ClassTag<EngagementMeta> CLASS_TAG = MODULE$.apply(EngagementMeta.class);
    private transient final JavaSparkContext sc;
    private final DataFrameExample dataFrameExample;

    @Override
    public JavaPairRDD<String, Tuple2<String, Long>> getGraphRDD() {
        return null;
    }

    @Override
    public List<Tuple2<String, Tuple2<String, Long>>> mostImportantEngagementLeaders(int limit) {
        JavaPairRDD<Long, Tuple2<Double, EngagementMeta>> engagementsRank = rankByEngagements();

        JavaPairRDD<Long, Long> longLongJavaPairRDD = engagementsRank.distinct().groupByKey().mapToPair(it -> apply(it._1, StreamSupport.stream(it._2().spliterator(), false).count()));
        JavaPairRDD<Long, Long> noDst = engagementsRank.groupByKey().mapToPair(it -> apply(it._1, StreamSupport.stream(it._2().spliterator(), false).count()));
        JavaPairRDD<Long, Tuple2<Long, Long>> filter = noDst.join(longLongJavaPairRDD).filter(it -> !Objects.equals(it._2._1, it._2._2));
        JavaPairRDD<Long, Tuple2<Tuple2<Double, EngagementMeta>, Tuple2<Long, Long>>> join = engagementsRank.join(filter);
        long relationDuplicationRate = join.values().map(it -> it._1._2).distinct().count();
        JavaPairRDD<Long, Tuple2<Double, EngagementMeta>> distinct = engagementsRank.distinct();
        JavaPairRDD<Tuple2<Long, Tuple2<Double, EngagementMeta>>, Tuple2<Long, Tuple2<Double, EngagementMeta>>> zip = engagementsRank.zip(distinct);
        JavaPairRDD<Long, Tuple2<Double, EngagementMeta>> backToPair = engagementsRank.mapToPair(it -> apply(it, 1L)).reduceByKey(Long::sum).filter(it -> it._2 > 1).mapToPair(it -> it._1);
        List<EngagementMeta> collect = join.map(it -> it._2._1._2).collect();


        return engagementsRank
                .mapToPair(it -> apply(it._2, it._2._2))
                .mapToPair(it -> apply(apply(it._2.userId, it._2.userName), 1L))
                .reduceByKey(Long::sum)
                .mapToPair(it -> apply(it._1._1, apply(it._1._2, it._2)))
                .take(limit);

//         engagementsRank
//                .mapToPair(it -> apply(it._2, it._2._2))
//                .mapToPair(it -> apply(apply(it._2().userId, it._2().userName), 1L))
//                .reduceByKey(Long::sum)
//                .mapToPair(it -> apply(it._1._1,  it._2));
//
//        engagementsRank.mapToPair(it -> apply(it._2._1, it._1)).sortByKey(false).mapToPair(it -> it.swap())
//                .join(engagementsRank.mapToPair(it -> apply(it._1, it._2._2)))
//
//        return engagementsRank
//                .mapToPair(it -> apply(it._2._1, it))
//                .sortByKey(false)
//                .mapToPair(it -> it.)
//                .reduceByKey(Long::sum)
//                .mapToPair(it -> apply(it._1._1, apply(it._1._2, it._2)))
//                .sortByKey()
//                .take(limit);
    }

    private JavaPairRDD<Long, Tuple2<Double, EngagementMeta>> rankByEngagements() {
        final JavaRDD<Engagement> engagementJavaRDD = sc
                .parallelize(dataFrameExample.collectAuraData());

        final JavaRDD<Edge<EngagementMeta>> edges = engagementJavaRDD
                .map(this::getEdges)
                .flatMap(List::iterator)
                .cache();
        JavaPairRDD<Long, EngagementMeta> users = edges.mapToPair(it -> apply(it.dstId(), it.attr()));

        final JavaPairRDD<Long, EngagementMeta> engagements = edges
                .mapToPair(it -> apply(it.srcId(), it.attr()));

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
        JavaPairRDD<Long, EngagementMeta> longEngagementMetaJavaPairRDD = engagements.mapToPair(it -> apply(it, 1L))
                .reduceByKey(Long::sum).filter(it -> it._2 > 1).mapToPair(it -> apply(it._1._1, it._1._2));
        // join so we can know the engagement leaders we just ranked
        List<Tuple2<Long, EngagementMeta>> res = longEngagementMetaJavaPairRDD.collect();

        return rank.join(engagements);
    }

    private Graph<EngagementMeta, EngagementMeta> getEngagementGraph(JavaRDD<Edge<EngagementMeta>> graphRDD) {
        return Graph.fromEdges(
                graphRDD.rdd(),
                new EngagementMeta(),
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
    private List<Edge<EngagementMeta>> getEdges(final Engagement engagement) {

        List<Edge<EngagementMeta>> collect = engagement.getLeaders()
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
                                new EngagementMeta(
                                        leader.getGuid(),
                                        leader.getFullName(),
                                        engagement.getName()
                                )
                        )))
                .collect(Collectors.toList());
        if (engagement.getName().equals("Copy_Aura_Patching_UK_April2019")) {
            System.out.println("hello");
        }
        return collect;
    }

    private Predicate<User> notNull() {
        return it -> !it.getGuid().equalsIgnoreCase("null");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    private static class EngagementMeta implements Serializable {
        private String userId;
        private String userName;
        private String engagementName;
    }
}
