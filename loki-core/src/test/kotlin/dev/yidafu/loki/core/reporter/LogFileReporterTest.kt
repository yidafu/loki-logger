package dev.yidafu.loki.core.reporter

import dev.yidafu.loki.core.sender.DefaultSender
import io.kotest.common.runBlocking
import io.kotest.core.spec.style.FunSpec
import kotlinx.coroutines.delay
import java.io.File

class LogFileReporterTest : FunSpec({

    test("log file reporter test") {
        File("/tmp/log/loki").mkdirs()
        val reporter = LogFileReporter("/tmp/log/loki", 1000, sender = DefaultSender())
        runBlocking {
            reporter.onStart()

            delay(3000)

            reporter.onStop()
        }
    }
})
