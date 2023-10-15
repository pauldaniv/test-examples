val apacheSparkVersion = "2.4.3"
val apacheSparkScalaVersion = "2.11"

dependencies {
  implementation(project(":common"))
  implementation(project(":common-interservice"))

  implementation("org.codehaus.groovy:groovy")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.kafka:spring-kafka")

  implementation("org.apache.spark:spark-core_$apacheSparkScalaVersion:$apacheSparkVersion")
  implementation("org.apache.spark:spark-sql_$apacheSparkScalaVersion:$apacheSparkVersion")
  implementation("org.apache.spark:spark-mllib_$apacheSparkScalaVersion:$apacheSparkVersion")

  implementation("org.apache.spark:spark-streaming-kafka_$apacheSparkScalaVersion:1.6.3") {
    exclude("org.apache.kafka", "kafka_2.11")
  }

  implementation("org.apache.kafka:kafka_$apacheSparkScalaVersion:0.8.2.1")
  implementation("com.datastax.spark:spark-cassandra-connector_$apacheSparkScalaVersion:$apacheSparkVersion")
  implementation("org.codehaus.janino:commons-compiler:3.0.8")
  implementation("org.codehaus.janino:janino:3.0.8")
  implementation("io.dropwizard.metrics:metrics-core:3.2.2")

  compileOnly("org.scala-lang:scala-library:$apacheSparkScalaVersion.8")

  testImplementation(
      "org.springframework.boot:spring-boot-starter-test",
      "com.tngtech.java:junit-dataprovider:1.13.1"
  )
  testImplementation(
      "org.easymock:easymock:4.2"
//      "org.junit.jupiter:junit-jupiter:5.6.2"
  )
}

//configurations {
//  // We don"t want the Spark dependencies in our shadowJar.
////  runtime.exclude group: "org.apache.spark"
//  all*.exclude module: "slf4j-log4j12"
//}

//test {
//  useTestNG()
//  testLogging {
//    events "passed", "skipped", "failed"
//  }
//}
