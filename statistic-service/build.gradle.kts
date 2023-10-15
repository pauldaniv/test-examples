val apacheSparkVersion = "2.4.3"
val apacheSparkScalaVersion = "2.11"

dependencies {
    implementation(project(":common"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:2.1.0.RELEASE")
    implementation("com.thoughtworks.paranamer:paranamer:2.8")

    implementation("org.apache.spark:spark-core_$apacheSparkScalaVersion:$apacheSparkVersion") {
        exclude("org.slf4j", "slf4j-log4j12")
    }

    testImplementation(
        "org.springframework.boot:spring-boot-starter-test",
        "com.tngtech.java:junit-dataprovider:1.13.1"
    )
}

//ext {
//  set("springCloudVersion", "Greenwich.RELEASE")
//}

//dependencyManagement {
//  imports {
//    mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
//  }
//}
//
//test {
//  useTestNG()
//}
