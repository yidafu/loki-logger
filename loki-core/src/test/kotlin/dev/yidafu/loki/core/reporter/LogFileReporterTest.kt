package dev.yidafu.loki.core.reporter

import dev.yidafu.loki.core.sender.DefaultSender
import io.kotest.common.runBlocking
import io.kotest.core.spec.style.FunSpec
import kotlinx.coroutines.delay

class LogFileReporterTest : FunSpec({

    test("log file reporter test") {
        val reporter = LogFileReporter("/tmp/log/loki", 1000, sender = DefaultSender())
        runBlocking {
            reporter.onStart()

            delay(3000)

            reporter.onStop()
        }
    }
})
