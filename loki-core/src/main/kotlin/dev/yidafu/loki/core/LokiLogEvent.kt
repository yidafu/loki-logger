package dev.yidafu.loki.core

import org.slf4j.MDC

class LokiLogEvent(
    override val timestamp: Long,
    override val topic: String,
    override val hostname: String,
    override val pid: String,
    override val env: String,
    override val level: Level,
    override val loggerName: String,
    override val tagMap: Map<String, String>,
    override val message: String,
) : ILogEvent {

    companion object {
        fun create(level: Level, loggerName: String, message: String): LokiLogEvent {
            val timestamp = System.currentTimeMillis()
            val map = MDC.getCopyOfContextMap()
            val topic = MDC.get(Constants.TAG_TOPIC) ?: "-"
            val hostname = MDC.get(Constants.TAG_HOSTNAME) ?: "-"
            val pid = MDC.get(Constants.TAG_PID)
            val env = MDC.get(Constants.TAG_ENV)
            val tagMap = map.filter {
                it.key != Constants.TAG_TOPIC ||
                    it.key == Constants.TAG_HOSTNAME ||
                    it.key == Constants.TAG_PID ||
                    it.key == Constants.TAG_ENV
            }.toMap()
            return LokiLogEvent(
                timestamp,
                topic,
                hostname,
                pid,
                env,
                level,
                loggerName,
                tagMap,
                message,
            )
        }
    }

    override fun prepare() {
        TODO("Not yet implemented")
    }
}
