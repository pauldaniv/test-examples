package com.pauldaniv.spark.graphx

import com.pauldaniv.spark.model.Engagement
import org.apache.spark.SparkContext
import org.apache.spark.graphx.{Edge, Graph, VertexRDD}
import org.springframework.stereotype.Component

import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.mutable

@Component
class GraphXExampleImplScala {

  private val sc: SparkContext = null
  private val dataFrameExample: DataFrameExample = null

  def getGraph(): Unit = {
    val engagements = dataFrameExample.collectAuraData

    val engagementVerticesRDD = sc.parallelize(asScalaBuffer(engagements).flatMap(getVertices))
    val engagementEdgesRDD = sc.parallelize(asScalaBuffer(engagements).flatMap(getEdges))

    val nowhere = EngagementMeta

    val graph: Graph[String, String] = Graph(engagementVerticesRDD, engagementEdgesRDD, nowhere)

    val vertices: VertexRDD[String] = graph.vertices

    vertices.foreach(println)
  }

  private def getVertices(engagement: Engagement): mutable.Buffer[(Long, EngagementMeta)] = {
    asScalaBuffer(engagement.getLeaders).map(it => (it.getGuid.hashCode, EngagementMeta(engagement.getGuid, engagement.getName)))
  }


  private def getEdges(engagement: Engagement): mutable.Buffer[Edge[String]] = {
    val leaders = asScalaBuffer(engagement.getLeaders)
    val members = asScalaBuffer(engagement.getMembers)
    leaders.flatMap(leader => {
      members.map(member => {
        Edge(leader.getGuid.hashCode, member.getGuid.hashCode, engagement.getGuid)
      })
    })
  }

  case class EngagementMeta(
                             var guid: String,
                             var name: String
                           )

}
