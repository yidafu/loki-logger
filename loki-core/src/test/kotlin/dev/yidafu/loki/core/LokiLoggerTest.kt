package dev.yidafu.loki.core

import dev.yidafu.loki.core.appender.ConsoleAppender
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.slf4j.MDC

class LokiLoggerTest : FunSpec({
    test("LokiLogger#log") {
        mockkStatic(MDC::getCopyOfContextMap)
        every { MDC.getCopyOfContextMap() } returns mutableMapOf(
            "topic" to "app",
            "hostname" to "localhost",
            "pid" to "123",
            "level" to "info",
            "tag" to "TagName",
            "custom" to "value",
        )
        val appender = mockk<ConsoleAppender>() {
            var state = false
            every { onStart() } answers {
                state = true
            }
            every { onStop() } answers {
                state = false
            }
            every { isStarted() } answers { state }
            every { doAppend(any()) } answers { }
        }

        appender.onStart()
        val logger = LokiLogger(LokiLogger.ROOT_LOGGER_NAME)
        logger.level shouldBe Level.Debug
        logger.isTraceEnabled.shouldBeFalse()
        logger.isInfoEnabled.shouldBeTrue()
        logger.addAppender(appender)
        logger.isAttached(appender).shouldBeTrue()
        logger.info("info message")
        logger.detachAppender(appender)
        appender.onStop()

        verify { appender.doAppend(any()) }
    }
})
