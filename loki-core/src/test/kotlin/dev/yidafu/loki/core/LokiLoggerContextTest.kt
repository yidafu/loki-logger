package dev.yidafu.loki.core

import dev.yidafu.loki.core.config.Configuration
import dev.yidafu.loki.core.reporter.NopReporter
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class LokiLoggerContextTest : FunSpec({

    test("LokiLoggerContext add reporter") {
        val context = LokiLoggerContext()
        context.config = Configuration()

        val root = context.getLogger(LokiLogger.ROOT_LOGGER_NAME)
        root.shouldNotBeNull()
        runBlocking {

            val reporter = spyk(NopReporter(20))
            context.addReporter(reporter)
            context.start()
            context.startReporters()
            delay(100)
            verify(atLeast = 4) { reporter.report() }
            verify(atLeast = 4) { reporter.report(any()) }

            context.stopReporters()
            context.stop()
        }
    }
})
