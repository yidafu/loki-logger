package dev.yidafu.loki.server

import dev.yidafu.loki.core.BaseServiceProvider
import dev.yidafu.loki.core.appender.ConsoleAppender
import dev.yidafu.loki.core.appender.FileAppender
import dev.yidafu.loki.core.reporter.LogFileReporter
import dev.yidafu.loki.core.sender.DefaultSender
import org.slf4j.MDC
import java.net.InetAddress

class ServerServiceProvider : BaseServiceProvider() {

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
            FileAppender(
                loggerContext.config.logDirectory,
                "FILE",
            ).apply {
                setEventBus(loggerContext)
            },
        )
        MDC.put("topic", config.topic)
        MDC.put("hostname", InetAddress.getLocalHost().hostName)
        MDC.put("pid", ProcessHandle.current().pid().toString())
        val env = System.getProperties().getProperty("spring.profiles.active", "dev")
        MDC.put("env", env)
        loggerContext.addReporter(
            LogFileReporter(
                config.logDirectory,
                config.reportInterval,
                sender = DefaultSender(),
            ),
        )
    }
}
