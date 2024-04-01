package dev.yidafu.loki.core.config

import dev.yidafu.loki.core.Level
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.days

@Serializable
data class Configuration(
    @SerialName("topic")
    val topic: String = "unknown",

    @SerialName("log-dir")
    val logDirectory: String = "/tmp/log/loki",

    @SerialName("http-endpoint")
    val httpEndpoint: String = "http://localhost:3000/loki/api/v1/push",

    @SerialName("naming-strategy")
    val namingStrategy: String = "date",

    @SerialName("report-interval")
    val reportInterval: Long = 5 * 1000,

    @SerialName("max-survival-time")
    val maxSurvivalTime: Long = 7.days.inWholeMilliseconds,

    @SerialName("log-level")
    val logLevel: String = Level.INFO_STR,
)
