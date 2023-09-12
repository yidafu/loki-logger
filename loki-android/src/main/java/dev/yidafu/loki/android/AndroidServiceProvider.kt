package dev.yidafu.loki.android

import dev.yidafu.loki.core.BaseServiceProvider
import dev.yidafu.loki.core.appender.ConsoleAppender
import dev.yidafu.loki.core.appender.FileAppender
import org.slf4j.MDC

class AndroidServiceProvider : BaseServiceProvider() {

    /**
     * Initialize the logging back-end.
     *
     *
     * **WARNING:** This method is intended to be called once by
     * [org.slf4j.LoggerFactory] class and from nowhere else.
     *
     */
    override fun initialize() {
        super.initialize()
        loggerContext.root.addAppender(
            ConsoleAppender("CONSOLE").apply {
                setEventBus(loggerContext)
            },
        )
        val config = loggerContext.config
        loggerContext.root.addAppender(
            FileAppender(config.logDirectory, config.namingStrategy).apply {
                setEventBus(loggerContext)
            }
        )
        MDC.put("topic", config.topic)
    }
}
