group = "dev.yidafu.loki"
version = "1.0-SNAPSHOT"

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `maven-publish`
    signing
}

val ossrhUsername: String by project
val ossrhPassword: String by project

android {
    namespace = "dev.yidafu.loki"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
//    implementation("com.android.support:appcompat-v7:28.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")

    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation(project(":loki-core"))
}

publishing {
    publications {
        create<MavenPublication>("mavenKotlin") {
            groupId = "dev.yidafu.loki"
            artifactId = "loki-android"
            version = "0.0.1"

            afterEvaluate {
                from(components["release"])
            }

            pom {
                artifactId = "loki-android"
//                from(components["release"])
//                artifact(tasks.kotlinSourcesJar)
//                artifact(dokkaJavadocJar)

                versionMapping {
                    usage("java-api") {
                        fromResolutionOf("runtimeClasspath")
                    }
                    usage("java-runtime") {
                        fromResolutionResult()
                    }
                }

                name.set("Loki Logger")
                description.set("A Loki Android Client")
                url.set("https://github.com/yidafu/loki-logger")
//                properties.set(mapOf(
//                    "myProp" to "value",
//                    "prop.with.dots" to "anotherValue"
//                ))
                distributionManagement {
                    relocation {
                        // New artifact coordinates
                        groupId.set("dev.yidafu.loki")
                        artifactId.set("android")
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
            name = "local"
            url = uri("${project.buildDir}/repo")
        }
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
