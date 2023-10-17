package dev.yidafu.loki.core

import org.slf4j.MDC
import java.time.Clock

/**
 * ILogEvent Implement
 */
class LokiLogEvent(
    override val timestamp: String,
    override val topic: String,
    override val hostname: String,
    override val pid: String,
    override val env: String,
    override val level: Level,
    override val tag: String,
    override val tagMap: Map<String, String>,
    override val message: String,
) : ILogEvent {

    /**
     * unique key of LogEvent
     */
    val uniqueKey by lazy {
        StringBuilder().apply {
            append(topic)
            append(',')
            append(hostname)
            append(',')
            append(pid)
            append(',')
            append(env)
            append(',')
            append(level)
            append(',')
            append(tag)
            append(',')
            append(tagMap.values.joinToString(","))
        }.toString()
    }

    /**
     * return all label. topic/hostname/pid/env/level/name is default label.
     */
    fun getMap(): Map<String, String> {
        return mapOf(
            "topic" to topic,
            "hostname" to hostname,
            "pid" to pid,
            "env" to env,
            "level" to level.toString(),
            "tag" to tag,
        ) + tagMap
    }

    override fun equals(other: Any?): Boolean {
        if (other is LokiLogEvent) {
            return timestamp == other.timestamp &&
                topic == other.topic &&
                hostname == other.hostname &&
                pid == other.pid &&
                env == other.env &&
                level == other.level &&
                tag == other.tag &&
                message == other.message &&
                tagMap == other.tagMap
        }
        return false
    }

    override fun hashCode(): Int {
        var result = timestamp.hashCode()
        result = 31 * result + topic.hashCode()
        result = 31 * result + hostname.hashCode()
        result = 31 * result + pid.hashCode()
        result = 31 * result + env.hashCode()
        result = 31 * result + level.hashCode()
        result = 31 * result + tag.hashCode()
        result = 31 * result + message.hashCode()
        result = 31 * result + tagMap.hashCode()

        return result
    }

    companion object {

        private fun getNanosecond(): Long {
            val clock = Clock.systemDefaultZone()
            val instant = clock.instant() // or Instant.now();

            val seconds = instant.epochSecond
            val nano = instant.nano.toLong()
            return seconds * 1_000_000_000 + nano
        }

        /**
         * create LokiLogEvent
         */
        fun create(level: Level, loggerName: String, message: String): LokiLogEvent {
            val timestamp = getNanosecond()
            val map = MDC.getCopyOfContextMap()
            val topic = map[Constants.TAG_TOPIC] ?: "-"
            val hostname = map[Constants.TAG_HOSTNAME] ?: "-"
            val pid = map[Constants.TAG_PID] ?: "-"
            val env = map[Constants.TAG_ENV] ?: "-"
            val tagMap = map.filter {
                it.key != Constants.TAG_TOPIC &&
                    it.key != Constants.TAG_HOSTNAME &&
                    it.key != Constants.TAG_PID &&
                    it.key != Constants.TAG_ENV
            }.toMap()
            return LokiLogEvent(
                timestamp.toString(),
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
}
