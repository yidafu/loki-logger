package dev.yidafu.loki.server

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.slf4j.MDC


class ServerLoggerTest {
    @Test
    fun testLogger() {
        MDC.put("key", "value")
        MDC.put("key2", "value2")
        val logger = LoggerFactory.getLogger(ServerLoggerTest::class.java)
        val var1 = 1234
        logger.info("test message {}", var1)
    }
}