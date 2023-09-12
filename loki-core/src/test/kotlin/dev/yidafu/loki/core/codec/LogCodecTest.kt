package dev.yidafu.loki.core.codec

import dev.yidafu.loki.core.Level
import dev.yidafu.loki.core.LokiLogEvent
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.test.assertEquals

class LogCodecTest : FunSpec({
    test("codec test case") {
        val event = LokiLogEvent(
            "1693232661802L",
            "topic",
            "local-hostname",
            "1234",
            "dev",
            Level.Info,
            "TestTag",
            mapOf("key" to "value", "key2" to "value2"),
            "message [key:value]\nxxx",
        )

        val log = LogCodec.encode(event)
        assertEquals("1693232661802L <topic> <local-hostname> <1234> <dev> INFO (TestTag) [key:value] [key2:value2] - message [key:value]\\nxxx", log)

        val event2 = LogCodec.decode(log)
        event.toString() shouldBe event2.toString()
    }
})
