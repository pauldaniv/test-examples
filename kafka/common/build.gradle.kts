import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("plugin.spring") version "1.9.10"
}

group = "com.pauldaniv"
version = "0.0.1-SNAPSHOT"

val packagesUrl = "https://maven.pkg.github.com/pauldaniv"

val githubUsr: String = findParam("gpr.usr", "USERNAME") ?: ""
val publishKey: String? = findParam("gpr.key", "GITHUB_TOKEN")
val packageKey = findParam("TOKEN", "PACKAGES_ACCESS_TOKEN") ?: publishKey

repositories {
  repoForName("s3-client") {
    maven(it)
  }
}

dependencies {
  api("com.pauldaniv.aws.s3:client:0.1.0-SNAPSHOT")

  implementation(project(":common"))
  implementation(project(":common-interservice"))
  implementation("com.pauldaniv.aws.s3:client:0.0.3-SNAPSHOT")

  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.springframework.cloud:spring-cloud-stream")
  implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka")
  implementation("org.springframework.kafka:spring-kafka")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.cloud:spring-cloud-stream-test-support")
  testImplementation("org.springframework.kafka:spring-kafka-test")
}

//tasks.withType<Test> {
//  useJUnitPlatform()
//}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "17"
  }
}

project.extra["library"] = true

fun repoForName(vararg repos: String, repoRegistrar: (MavenArtifactRepository.() -> Unit) -> Unit) = repos.forEach {
  val maven: MavenArtifactRepository.() -> Unit = {
    name = "GitHubPackages"
    url = uri("$packagesUrl/$it")
    credentials {
      username = githubUsr
      password = packageKey
    }
  }
  repoRegistrar.invoke(maven)
}

fun findParam(vararg names: String): String? {
  for (name in names) {
    val param = project.findProperty(name) as String? ?: System.getenv(name)
    if (param != null) {
      return param
    }
  }
  return null
}
