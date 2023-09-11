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

            loggerContext.start()
            val logger = LoggerFactory.getLogger(ServerLoggerTest::class.java)
            val var1 = 1234
            repeat(10) {
                logger.info("test message {}", var1)
            }
            delay(1500)
            repeat(10) {
                logger.info("Suppressed: kotlinx.coroutines.internal.DiagnosticCoroutineContextException: [CoroutineId(2), \"coroutine#2\":StandaloneCoroutine{Cancelling}@72ebba7b, Dispatchers.IO]")
            }
            delay(10 * 1000)
            loggerContext.stop()
        }
    }
}
