package dev.yidafu.loki.core

class LogEvent(
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
    override fun equals(other: Any?): Boolean {
        if (other is LogEvent) {
            return (
                timestamp == other.timestamp &&
                    topic == other.topic &&
                    hostname == other.hostname &&
                    pid == other.pid &&
                    env == other.env &&
                    level == other.level &&
                    loggerName == other.loggerName &&
                    tagMap == other.tagMap &&
                    message == other.message
                )
        }
        return false
    }
    override fun prepare() {
        TODO("Not yet implemented")
    }

    override fun hashCode(): Int {
        var result = timestamp.hashCode()
        result = 31 * result + topic.hashCode()
        result = 31 * result + hostname.hashCode()
        result = 31 * result + pid.hashCode()
        result = 31 * result + env.hashCode()
        result = 31 * result + level.hashCode()
        result = 31 * result + loggerName.hashCode()
        result = 31 * result + tagMap.hashCode()
        result = 31 * result + message.hashCode()
        return result
    }
}
