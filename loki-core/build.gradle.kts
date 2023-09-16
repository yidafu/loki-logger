import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "dev.yidafu.loki"
version = "0.0.1"

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    `maven-publish`
    signing
}

val ossrhUsername: String by project
val ossrhPassword: String by project

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

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}


tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

val dokkaJavadocJar = tasks.register<Jar>("dokkaJavadocJar") {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

publishing {
    publications {
        create<MavenPublication>("mavenKotlin") {
            pom {
                artifactId = "loki-core"
                from(components["java"])
                artifact(tasks.kotlinSourcesJar)
                artifact(dokkaJavadocJar)

                versionMapping {
                    usage("java-api") {
                        fromResolutionOf("runtimeClasspath")
                    }
                    usage("java-runtime") {
                        fromResolutionResult()
                    }
                }

                name.set("Loki Logger")
                description.set("A Loki Client Implement")
                url.set("https://github.com/yidafu/loki-logger")
//                properties.set(mapOf(
//                    "myProp" to "value",
//                    "prop.with.dots" to "anotherValue"
//                ))
                distributionManagement {
                    relocation {
                        // New artifact coordinates
                        groupId.set("dev.yidafu.loki")
                        artifactId.set("lib")
                        version.set("0.0.1")
                        message.set("groupId has been changed")
                    }
                }

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("dovyih")
                        name.set("Dov Yih")
                        email.set("me@yidafu.dev")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com:yidafu/loki-logger.git")
                    developerConnection.set("scm:git:ssh://github.com:yidafu/loki-logger.git")
                    url.set("https://github.com:yidafu/loki-logger/")
                }
            }
        }
    }

    repositories {
        maven {
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            // 这里就是之前在issues.sonatype.org注册的账号
            credentials {
                username = ossrhUsername
                password = ossrhPassword
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenKotlin"])
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    disabledRules.set(setOf("final-newline", "no-wildcard-imports"))
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

tasks.register<Jar>("dokkaHtmlJar") {
    dependsOn(tasks.dokkaHtml)
    from(tasks.dokkaHtml.flatMap { it.outputDirectory })
    archiveClassifier.set("html-docs")
}

tasks.register<Jar>("generateSourceJar") {
    group = "jar"
    from(sourceSets["main"].kotlin.srcDirs)
    archiveClassifier.set("sources")
}

afterEvaluate {
    val jarTask = tasks.findByName("jar")
    jarTask?.finalizedBy(tasks.findByName("generateSourceJar"))
    jarTask?.finalizedBy(tasks.findByName("dokkaJavadocJar"))
}
