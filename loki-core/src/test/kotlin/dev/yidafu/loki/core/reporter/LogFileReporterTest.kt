package dev.yidafu.loki.core.reporter

import dev.yidafu.loki.core.sender.HttpSender
import io.kotest.common.runBlocking
import io.kotest.core.spec.style.FunSpec
import kotlinx.coroutines.delay

class LogFileReporterTest: FunSpec( {

    test("log file reporter test") {
        val reporter = LogFileReporter("/tmp/log/loki", 1000, sender =  HttpSender("http://localhost:3000"))
        runBlocking {
            reporter.onStart()

            delay(3000)

            reporter.onStop()
        }
    }
})