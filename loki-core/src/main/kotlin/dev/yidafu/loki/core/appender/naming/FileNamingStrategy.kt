package dev.yidafu.loki.core.appender.naming

interface FileNamingStrategy {
    val name: String
    fun generate(level: Int, timestamp: Long): String
}
