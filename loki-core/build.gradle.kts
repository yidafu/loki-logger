group = "dev.yidafu.loki.core"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
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
    // https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
//    implementation("ch.qos.logback:logback-classic:1.4.11")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("com.charleskorn.kaml:kaml:0.55.0")

    testImplementation("io.kotest:kotest-runner-junit5:5.7.1")
    testImplementation("io.kotest:kotest-assertions-core:5.7.1")
    testImplementation("io.kotest:kotest-property:5.7.1")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
