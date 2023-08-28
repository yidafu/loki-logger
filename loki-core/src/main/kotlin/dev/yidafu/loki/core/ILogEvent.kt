package dev.yidafu.loki.core

import dev.yidafu.loki.core.hook.PrepareLifecycleHook

interface ILogEvent : PrepareLifecycleHook {
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
