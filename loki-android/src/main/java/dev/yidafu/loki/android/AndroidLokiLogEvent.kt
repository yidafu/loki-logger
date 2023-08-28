package dev.yidafu.loki.android

import dev.yidafu.loki.core.ILogEvent
import dev.yidafu.loki.core.Level

class AndroidLokiLogEvent(
    override val timestamp: Long = System.currentTimeMillis(),
    override val topic: String,
    override val hostname: String,
    override val pid: String,
    override val env: String,
    override val level: Level,
    override val loggerName: String,
    override val tagMap: Map<String, String>,
    override val message: String,
) : ILogEvent {

    override fun prepare() {
        TODO("Not yet implemented")
    }
}
