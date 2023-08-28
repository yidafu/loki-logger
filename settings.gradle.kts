include(":loki-android2")

rootProject.name = "loki-logger"

buildscript {
    repositories {
        maven {
            // name "腾讯云镜像"
            setUrl("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
        }
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("org.jlleitschuh.gradle:ktlint-gradle:11.5.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
        // https://mvnrepository.com/artifact/com.android.tools.build/gradle
        classpath("com.android.tools.build:gradle:7.4.1")

    }
}

include("loki-core")
include("loki-server")
include("loki-android")
// include("server-example")
