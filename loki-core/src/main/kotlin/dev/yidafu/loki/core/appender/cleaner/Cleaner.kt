package dev.yidafu.loki.core.appender.cleaner
import dev.yidafu.loki.core.listener.EventListener

/**
 * clean log file
 */
interface Cleaner : EventListener {
    abstract val logDir: String

    fun clean()
}
