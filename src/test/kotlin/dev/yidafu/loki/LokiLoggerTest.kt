package dev.yidafu.loki

import org.slf4j.LoggerFactory
import kotlin.test.Test

class LokiLoggerTest {
    @Test
    fun testGetLogger() {
        val logger = LoggerFactory.getLogger("Tag")
        logger.info("info message")
    }
}
