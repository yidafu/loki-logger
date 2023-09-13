package dev.yidafu.loki.core.appender.naming

/**
 * generate log file name
 */
interface FileNamingStrategy {
    val name: String

    /**
     * @param level log level
     * @param timestamp
     */
    fun generate(level: Int, timestamp: Long): String
}
