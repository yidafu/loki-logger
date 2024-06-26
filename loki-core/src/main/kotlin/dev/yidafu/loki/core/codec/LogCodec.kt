package dev.yidafu.loki.core.codec

import dev.yidafu.loki.core.ILogEvent
import dev.yidafu.loki.core.Level
import dev.yidafu.loki.core.LokiLogEvent

/**
 * ILogEvent <=> Strings
 */
object LogCodec : ICodec<ILogEvent> {
    /**
     * transform [ILogEvent] to **one** line String.
     * [ILogEvent.message] will replace '\n' to '\\n'
     */
    override fun encode(event: ILogEvent): String {
        return StringBuilder().apply {
            append(event.timestamp)
            append(" <")
            append(event.topic)
            append("> <")
            append(event.hostname)
            append("> <")
            append(event.pid)
            append("> <")
            append(event.env)
            append("> ")
            append(event.level.toString())
            append(" (")
            append(event.loggerName)
            append(") ")
            event.tagMap.forEach { (key, value) ->
                append("[$key:$value] ")
            }
            append("- ")
            append(event.message.replace("\n", "\\n"))
        }.toString()
    }

    /**
     * decode string to [ILogEvent]
     *
     * String format
     * ```
     * timestamp <topic> <hostname/machine id> <pid/thread name> <env> Level (tag name) [key:value] [key:value] - message content
     * ````
     */
    override fun decode(raw: String): ILogEvent {
        try {
            val firstWhitespace = raw.indexOf(WHITESPACE)
            val timestamp = raw.slice(0..<firstWhitespace)

            val topicIndex = raw.indexOf(WHITESPACE, firstWhitespace + 1)
            val topic = raw.slice((firstWhitespace + 2)..<(topicIndex - 1))

            val hostnameIndex = raw.indexOf(WHITESPACE, topicIndex + 1)
            val hostname = raw.slice((topicIndex + 2)..<(hostnameIndex - 1))

            val pidIndex = raw.indexOf(WHITESPACE, hostnameIndex + 1)
            val pid = raw.slice((hostnameIndex + 2)..<(pidIndex - 1))

            val envIndex = raw.indexOf(WHITESPACE, pidIndex + 1)
            val env = raw.slice((pidIndex + 2)..<(envIndex - 1))

            val levelIndex = raw.indexOf(WHITESPACE, envIndex + 1)
            val level = raw.slice((envIndex + 1)..<levelIndex)

            val loggerIndex = raw.indexOf(WHITESPACE, levelIndex + 1)
            val loggerName = raw.slice((levelIndex + 2)..<(loggerIndex - 1))

            var leftIndex: Int = loggerIndex
            val tagMap = mutableMapOf<String, String>()
            while (raw[leftIndex + 1] == '[') {
                val rightIndex = raw.indexOf(']', leftIndex)
                val pair = raw.slice((leftIndex + 2)..<rightIndex)
                val (key, value) = pair.split(':')
                tagMap[key] = value
                leftIndex = rightIndex + 1
            }
            val msgLeftIndex = leftIndex.coerceAtLeast(loggerIndex) + 1
            val msgIndex = raw.indexOf('-', msgLeftIndex)
            val msg = raw.slice((msgIndex + 2)..<raw.length).replace("\\n", "\n")
            return LokiLogEvent(
                timestamp,
                topic,
                hostname,
                pid,
                env,
                Level.from(level),
                loggerName,
                tagMap,
                msg,
            )
        } catch (e: Throwable) {
            println()
            println(raw)
            println()
            throw e
        }
    }

    private const val WHITESPACE = ' '
}
