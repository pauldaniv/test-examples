import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("plugin.spring") version "1.4.10"
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
  implementation(project(":common"))
  implementation(project(":common-interservice"))
  implementation(project(":kafka-common"))

  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.springframework.cloud:spring-cloud-stream")
  implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka")
  implementation("org.springframework.kafka:spring-kafka")
  implementation("org.springframework.data:spring-data-commons")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.cloud:spring-cloud-stream-test-support")
  testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "1.8"
  }
}


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


tasks.register("startPostgres") {
  doLast {
    if (isDockerRunning()) {
      println("Postgres is already running. Skipping...")
    } else {
      println("Bringing up Postgres container...")
      startDocker()
    }
  }
}

tasks.register("stopPostgres") {
  doLast {
    if (isDockerRunning()) {
      stopDocker()
    } else {
      println("Postgres is already stopped. Skipping...")
    }
    if (!remoteBuild()) {
      rmDocker()
    }
  }
}

tasks.clean {
  dependsOn(tasks.findByName("stopPostgres"))
}

fun remoteBuild(): Boolean = (System.getenv("REMOTE_BUILD") ?: "false").toBoolean()

fun dbURL() = "jdbc:postgresql://${dbHost()}:${dbPort()}/${dbName()}"

fun dbHost() = findParam("DB_HOST") ?: "localhost"
fun dbPort() = findParam("DB_PORT") ?: 56320
fun dbUser() = findParam("DB_USER") ?: "kafka"
fun dbPass() = findParam("DB_PASS") ?: "kafka"
fun dbName() = findParam("DB_NAME") ?: "kafka"

fun findParam(name: String): String? = project.findProperty(name) as String? ?: System.getenv(name)

val dbContainer = "kafka-examples-postgres-db"

fun isPostgresHealthy(containerName: String = dbContainer) = listOf("docker", "exec", containerName, "psql", "-c", "select version()", "-U", dbUser())
    .exec()
    .contains("PostgreSQL 12.4.*compiled by".toRegex())

fun isDockerRunning(containerName: String = dbContainer) = "docker container inspect -f '{{.State.Status}}' $containerName".exec().contains("running")

fun startDocker(containerName: String = dbContainer) {
  """
  docker run --name $containerName
  -e POSTGRES_PASSWORD=${dbPass()}
  -e POSTGRES_USER=${dbUser()}
  -e POSTGRES_DB=${dbName()}
  -p ${dbPort()}:5432
  -v ${System.getProperty("user.home")}/db-data/$dbContainer:/var/lib/postgresql/data
  -d postgres:12-alpine""".trimIndent().exec()
  println("Waiting for container to be healthy...")

  var count = 0
  while (!isPostgresHealthy() && count < 20) {
    count++
    Thread.sleep(1000L)
    println(count)
    println("Retrying...")
  }
  if (count >= 20) {
    println("Unable to bring up postgres container...")
  } else {
    println("Container is up!")
  }
}

fun stopDocker(containerName: String = dbContainer) {
  val exec = "docker stop $containerName".exec()
  println(exec)
}

fun rmDocker(containerName: String = dbContainer) = "docker rm -v $containerName".exec()

fun List<String>.exec(workingDir: File = file("./")): String {
  val proc = ProcessBuilder(*this.toTypedArray())
      .directory(workingDir)
      .redirectErrorStream(true)
      .redirectOutput(ProcessBuilder.Redirect.PIPE)
      .redirectError(ProcessBuilder.Redirect.PIPE)
      .start()

  proc.waitFor(1, TimeUnit.MINUTES)
  return proc.inputStream.bufferedReader().readLines().joinToString("\n")
}

fun String.exec(): String {
  val parts = this.split("\\s".toRegex())
  return parts.toList().exec()
}
