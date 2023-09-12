package dev.yidafu.loki.core.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
)
