package dev.yidafu.loki.server

import dev.yidafu.loki.core.LokiLoggerContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.slf4j.MDC


class ServerLoggerTest {
    @Test
    fun testLogger() {
        runBlocking {
            val loggerContext = LoggerFactory.getILoggerFactory() as LokiLoggerContext
            MDC.put("topic", "test")
            MDC.put("hostname", "localhost")
            MDC.put("pid", "123")
            MDC.put("env", "dev")
            MDC.put("key", "value")
            loggerContext.start()
            val logger = LoggerFactory.getLogger(ServerLoggerTest::class.java)
            val var1 = 1234
            logger.info("test message {}", var1)

            delay(1000)
            loggerContext.stop()
        }

    }
}