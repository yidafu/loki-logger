
group = "dev.yidafu.loki"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm")
}

repositories {
    maven {
        // name "腾讯云镜像"
        setUrl("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
    }
    mavenCentral()
}
dependencies {
    testImplementation(kotlin("test"))
    implementation("org.slf4j:slf4j-api:2.0.7")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
//    implementation("org.slf4j:slf4j-simple:2.0.7")
    // implementation("ch.qos.logback:logback-classic:1.4.11")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation(project(mapOf("path" to ":loki-core")))
}

tasks.test {
    useJUnitPlatform()
}
