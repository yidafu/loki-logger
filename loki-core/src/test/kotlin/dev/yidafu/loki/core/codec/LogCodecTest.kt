package dev.yidafu.loki.core.codec

import dev.yidafu.loki.core.Level
import dev.yidafu.loki.core.LogEvent
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LogCodecTest {
    @Test
    fun codecTest() {
        val event = LogEvent(
            1693232661802L,
            "topic",
            "local-hostname",
            "1234",
            "dev",
            Level.Info,
            "TestTag",
            mapOf("key" to "value", "key2" to "value2"),
            "message",
        )

        val log = LogCodec.encode(event)
        assertEquals("1693232661802 <topic> <local-hostname> <1234> <dev> INFO (TestTag) [key:value] [key2:value2] - message", log)

        val event2 = LogCodec.decode(log)
        assertEquals(event, event2)
    }
}
