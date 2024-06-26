package dev.yidafu.loki.core

import dev.yidafu.loki.core.config.Configuration
import dev.yidafu.loki.core.listener.EventBus
import dev.yidafu.loki.core.listener.EventBusDelegate
import dev.yidafu.loki.core.reporter.Reporter
import org.slf4j.ILoggerFactory
import org.slf4j.Logger
import java.util.concurrent.ConcurrentHashMap

class LokiLoggerContext : ILoggerFactory, EventBus by EventBusDelegate {
    private val loggerMap: MutableMap<String, LokiLogger> = ConcurrentHashMap()
    lateinit var config: Configuration

    val root: LokiLogger = LokiLogger(LokiLogger.ROOT_LOGGER_NAME)
    val reporters = mutableListOf<Reporter>()

    init {
        loggerMap[root.name] = root
        root.level = Level.Debug
    }

    /**
     * Return an appropriate [Logger] instance as specified by the
     * `name` parameter.
     *
     *
     * If the name parameter is equal to [Logger.ROOT_LOGGER_NAME], that is
     * the string value "ROOT" (case-insensitive), then the root logger of the
     * underlying logging system is returned.
     *
     *
     * Null-valued name arguments are considered invalid.
     *
     *
     * Certain extremely simple logging systems, e.g. NOP, may always
     * return the same logger instance regardless of the requested name.
     *
     * @param name the name of the Logger to return
     * @return a Logger instance
     */
    override fun getLogger(name: String): LokiLogger {
        if (name === LokiLogger.ROOT_LOGGER_NAME) {
            return root
        }

        val logger = loggerMap[name]
        if (logger != null) {
            return logger
        }

        return name.split(".")
            .fold(mutableListOf<MutableList<String>>()) { acc, s ->
                if (acc.isEmpty()) {
                    acc.add(mutableListOf(s))
                } else {
                    val last = acc.last()
                    val newList: MutableList<String> = last.toMutableList()
                    newList.add(s)
                    acc.add(newList)
                }
                acc
            }
            .map { it.joinToString(".") }
            .fold(root) { l, childName ->
                val childLogger = l.getChildByName(childName)

                childLogger ?: run {
                    val cLogger = l.createChildByName(childName)
                    loggerMap[childName] = cLogger
                    cLogger
                }
            }
    }

    fun addReporter(reporter: Reporter) {
        reporters.add(reporter)
    }

    fun startReporters() {
        reporters.forEach { it.onStart() }
    }

    fun stopReporters() {
        reporters.forEach { it.onStop() }
    }

    fun start() {
        emitStart()
    }

    fun stop() {
        emitStop()
    }
}
