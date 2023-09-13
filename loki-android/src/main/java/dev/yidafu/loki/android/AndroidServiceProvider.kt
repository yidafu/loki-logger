package dev.yidafu.loki.android

import android.os.Build
import com.therouter.getApplicationContext
import dev.yidafu.loki.core.BaseServiceProvider
import dev.yidafu.loki.core.LokiLoggerContext
import dev.yidafu.loki.core.LokiMDCAdapter
import dev.yidafu.loki.core.appender.FileAppender
import dev.yidafu.loki.core.config.Configuration
import dev.yidafu.loki.core.reporter.LogFileReporter
import dev.yidafu.loki.core.sender.DefaultSender
import org.slf4j.MDC
import org.slf4j.helpers.BasicMarkerFactory

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
        loggerContext = LokiLoggerContext()
        mdcAdapter = LokiMDCAdapter()
        markerFactory = BasicMarkerFactory()

        val config = Configuration(
            httpEndpoint = "${BuildConfig.APP_SERVICE_URL}/loki/api/v1/push",
            logDirectory = getApplicationContext()!!.filesDir.absolutePath + "/log",
            topic = "hzsw-app",
        )
        loggerContext.config = config

        loggerContext.root.addAppender(
            AndroidAppender().apply {
                setEventBus(loggerContext)
            },
        )
        loggerContext.root.addAppender(
            FileAppender(config.logDirectory, config.namingStrategy).apply {
                setEventBus(loggerContext)
            },
        )
        loggerContext.addReporter(LogFileReporter(
            config.logDirectory,
            config.reportInterval,
            sender = DefaultSender()
        ))
        MDC.put("topic", config.topic)
        MDC.put("hostname", "hostname")
        MDC.put("pid", Thread.currentThread().name)
        val env = if (BuildConfig.DEBUG) "dev" else "prod"
        MDC.put("env", env)
        MDC.put("brand", Build.BRAND)
        MDC.put("model", Build.MODEL)
        MDC.put("product", Build.PRODUCT)
        MDC.put("fingerprint", Build.FINGERPRINT)
        MDC.put("osRelease", Build.VERSION.RELEASE)
        MDC.put("osSdk", Build.VERSION.SDK)
    }
