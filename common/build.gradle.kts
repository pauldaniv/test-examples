import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    idea
    groovy
    id("org.springframework.boot") version "2.2.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

apply(plugin = "io.spring.dependency-management")

version = "0.0.1-SNAPSHOT"
val springBootVersion = "2.1.+"

repositories {
    mavenCentral()
//    jcenter()
}

dependencies {
    implementation("javax.ws.rs:javax.ws.rs-api:2.1.1")
    implementation("io.github.openfeign:feign-okhttp:9.3.1")
    implementation("io.github.openfeign:feign-gson:9.3.1")
    implementation("io.github.openfeign:feign-slf4j:9.3.1")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("io.springfox:springfox-swagger2:2.9.2")
    implementation("io.springfox:springfox-swagger-ui:2.9.2")
    implementation("org.modelmapper:modelmapper:2.3.2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.codehaus.groovy:groovy:3.0.0-alpha-4")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    runtimeOnly("com.h2database:h2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}
