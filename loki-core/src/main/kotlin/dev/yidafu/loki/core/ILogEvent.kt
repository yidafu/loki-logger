package dev.yidafu.loki.core

interface ILogEvent {
    abstract val timestamp: Long

    abstract val topic: String

    abstract val hostname: String

    abstract val pid: String

    abstract val env: String

    abstract val level: Level

    abstract val loggerName: String

    abstract val tagMap: Map<String, String>

    abstract val message: String
}
