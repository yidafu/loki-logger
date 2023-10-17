package dev.yidafu.loki.core.reporter

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import java.io.File

class LogMetaTest : FunSpec({
    test("LogMeta") {
        File("/tmp/log-met-test").mkdirs()
        val logMeta = LogMeta("/tmp/log-met-test/meta.log", 0, 0)
        logMeta.logFile.writeText("long long line")
        logMeta.isReportable().shouldBeTrue()
    }
})
