package dev.yidafu.loki.example.android

import android.app.Application
import android.content.Context
import dev.yidafu.loki.android.YLog
import dev.yidafu.loki.core.LokiLoggerContext
import org.slf4j.LoggerFactory

class ExampleApplication : Application() {
    override fun attachBaseContext(base: Context?) {
        if (base != null) {
            YLog.setContext(base)
        }
        val loggerContext = LoggerFactory.getILoggerFactory() as LokiLoggerContext
        loggerContext.start()
        super.attachBaseContext(base)
    }

    override fun onTerminate() {
        super.onTerminate()

        val loggerContext = LoggerFactory.getILoggerFactory() as LokiLoggerContext
        loggerContext.stop()
        loggerContext.stopReporters()
    }
}
