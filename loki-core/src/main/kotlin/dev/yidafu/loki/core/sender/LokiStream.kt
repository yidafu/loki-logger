package dev.yidafu.loki.core.sender

/**
 * ```json
 * {
 *   "streams": [
 *     {
 *       "stream": {
 *         "label": "value"
 *       },
 *       "values": [
 *           [ "<unix epoch in nanoseconds>", "<log line>" ],
 *           [ "<unix epoch in nanoseconds>", "<log line>" ]
 *       ]
 *     }
 *   ]
 * }
 * ```
 */
data class LokiStream(
    val labels: Map<String, String>,
    val values: Array<Array<String>>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LokiStream

        if (labels != other.labels) return false
        return values.contentDeepEquals(other.values)
    }

    override fun toString(): String {
        return "{\"stream\":{${
            labels.entries.joinToString(",") { "\"${it.key}\":\"${it.value}\"" }
        }},\"values\":[${
            values.joinToString(",") { "[\"${it[0]}\",\"${it[1]}\"]" }
        }]}"
    }

    override fun hashCode(): Int {
        var result = labels.hashCode()
        result = 31 * result + values.contentDeepHashCode()
        return result
    }
}

data class LokiSteams(
    val streams: Array<LokiStream>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LokiSteams

        return streams.contentEquals(other.streams)
    }

    override fun toString(): String {
        return "{\"streams\":[${streams.joinToString(",") { it.toString()}}]}"
    }

    override fun hashCode(): Int {
        return streams.contentHashCode()
    }
}