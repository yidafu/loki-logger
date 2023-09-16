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
            append(event.tag)
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
        val firstWhitespace = raw.indexOf(WHITESPACE)
        val timestamp = if (firstWhitespace > -1) {
            raw.slice(0..<firstWhitespace)
        } else {
            // 上报时过滤掉
            ""
        }
        var index = firstWhitespace

        val topicIndex = raw.indexOf(WHITESPACE, index + 1)
        val topic = if (topicIndex > -1) {
            val s = raw.slice((index + 2)..<(topicIndex - 1))
            index = topicIndex
            s
        } else {
            "Unknown"
        }

        val hostnameIndex = raw.indexOf(WHITESPACE, index + 1)
        val hostname = if (hostnameIndex > -1) {
            val s = raw.slice((index + 2)..<(hostnameIndex - 1))
            index = hostnameIndex
            s
        } else {
            "-"
        }

        val pidIndex = raw.indexOf(WHITESPACE, index + 1)
        val pid = if (pidIndex > -1) {
            val s = raw.slice((index + 2)..<(pidIndex - 1))
            index = pidIndex
            s
        } else {
            "-1"
        }

        val envIndex = raw.indexOf(WHITESPACE, index + 1)
        val env = if (envIndex > -1) {
            val s = raw.slice((index + 2)..<(envIndex - 1))
            index = envIndex
            s
        } else {
            "-"
        }

        val levelIndex = raw.indexOf(WHITESPACE, index + 1)
        val level = if (levelIndex > -1) {
            val s = raw.slice((index + 1)..<levelIndex)
            index = levelIndex
            s
        } else {
            Level.INFO_STR
        }

        val tagIndex = raw.indexOf(WHITESPACE, index + 1)
        val tag = if (tagIndex > -1) {
            val s = raw.slice((index + 2)..<(tagIndex - 1))
            index = tagIndex
            s
        } else {
            "Unknown"
        }

        val tagMap = mutableMapOf<String, String>()
        while (index + 1 < raw.length && raw[index + 1] == '[') {
            val rightIndex = raw.indexOf(']', index)
            if (rightIndex > -1) {
                val pair = raw.slice((index + 2)..<rightIndex)
                val (key, value) = pair.split(':')
                tagMap[key] = value
                index = rightIndex + 1
            } else {
                break
            }
        }
        val msgLeftIndex = index + 1
        val msgIndex = raw.indexOf('-', msgLeftIndex)
        val msg = if (msgIndex > -1) {
            raw.slice((msgIndex + 2)..<raw.length).replace("\\n", "\n")
        } else {
            ""
        }

        return LokiLogEvent(
            timestamp,
            topic,
            hostname,
            pid,
            env,
            Level.from(level),
            tag,
            tagMap,
            msg,
        )
    }

    private const val WHITESPACE = ' '
}
