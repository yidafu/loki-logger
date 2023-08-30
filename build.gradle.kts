import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "dev.yidafu.loki"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm") apply false
    id("org.jlleitschuh.gradle.ktlint") apply false
    id("com.android.library") apply false
    id("org.jetbrains.kotlin.android") apply false
}
allprojects {
    repositories {
        maven {
            // name "腾讯云镜像"
            setUrl("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
        }
        google()
        mavenCentral()
    }
}

subprojects {
//    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

//    tasks.withType<KotlinCompile> {
//        kotlinOptions.jvmTarget = "1.8"
//    }

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("0.50.0")
        debug.set(true)
        android.set(false)
        outputToConsole.set(true)
        outputColorName.set("RED")

        disabledRules.set(setOf("final-newline", "no-wildcard-imports")) // not supported with ktlint 0.48+
//        reporters {
//            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
//            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
//        }
//    kotlinScriptAdditionalPaths {
//        include(fileTree("scripts/"))
//    }
//    filter {
//        exclude("**/generated/**")
//        include("**/kotlin/**")
//    }
    }
}
