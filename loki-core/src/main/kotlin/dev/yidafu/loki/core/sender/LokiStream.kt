package dev.yidafu.loki.core.sender

/**
 * copy from kotlinx.serialization.json.internal
 */

private const val STRING = '"'
private fun toHexChar(i: Int): Char {
    val d = i and 0xf
    return if (d < 10) {
        (d + '0'.code).toChar()
    } else {
        (d - 10 + 'a'.code).toChar()
    }
}

private val ESCAPE_STRINGS: Array<String?> = arrayOfNulls<String>(93).apply {
    for (c in 0..0x1f) {
        val c1 = toHexChar(c shr 12)
        val c2 = toHexChar(c shr 8)
        val c3 = toHexChar(c shr 4)
        val c4 = toHexChar(c)
        this[c] = "\\u$c1$c2$c3$c4"
    }
    this['"'.code] = "\\\""
    this['\\'.code] = "\\\\"
    this['\t'.code] = "\\t"
    this['\b'.code] = "\\b"
    this['\n'.code] = "\\n"
    this['\r'.code] = "\\r"
    this[0x0c] = "\\f"
}

private fun StringBuilder.printQuoted(value: String) {
    append(STRING)
    var lastPos = 0
    for (i in value.indices) {
        val c = value[i].code
        if (c < ESCAPE_STRINGS.size && ESCAPE_STRINGS[c] != null) {
            append(value, lastPos, i) // flush prev
            append(ESCAPE_STRINGS[c])
            lastPos = i + 1
        }
    }

    if (lastPos != 0) {
        append(value, lastPos, value.length)
    } else {
        append(value)
    }
    append(STRING)
}

/**
 * Loki report data format
 * 
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
        return StringBuilder().apply {
            append("{\"stream\":{")
            labels.entries.forEachIndexed { index, entry ->
                printQuoted(entry.key)
                append(':')
                printQuoted(entry.value)
                if (index != labels.size - 1) {
                    append(",")
                }
            }
            append("},\"values\":[")
            values.forEachIndexed { index, strings ->
                append("[")
                printQuoted(strings[0])
                append(",")
                printQuoted(strings[1])
                append("]")
                if (index != values.size - 1) {
                    append(",")
                }
            }
            append("]}")
        }.toString()
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
