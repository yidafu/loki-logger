package dev.yidafu.loki.core.codec

import dev.yidafu.loki.core.Level
import dev.yidafu.loki.core.LokiLogEvent
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
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
        assertEquals("1693232661802L <topic> <local-hostname> <1234> <dev> info (TestTag) [key:value] [key2:value2] - message [key:value]\\nxxx", log)

        val event2 = LogCodec.decode(log)
        event.toString() shouldBe event2.toString()
    }

    test("只有部分时间戳") {
        val event = LogCodec.decode("1693")
        event.timestamp.shouldBeEmpty()
    }

    test("只有时间戳") {
        val event = LogCodec.decode("1693232661802 <to")
        event.timestamp shouldBe "1693232661802"
        event.topic shouldBe "Unknown"
    }

    test("missing hostname") {
        val event = LogCodec.decode("1693232661802 <topic> local")
        event.topic shouldBe "topic"
        event.hostname shouldBe "-"
    }

    test("missing pid") {
        val event = LogCodec.decode("1693232661802 <topic> <localhost> ")
        event.hostname shouldBe "localhost"
        event.pid shouldBe "-1"
    }

    test("missing env") {
        val event = LogCodec.decode("1693232661802 <topic> <localhost> <123> <te")
        event.pid shouldBe "123"
        event.env shouldBe "-"
    }

    test("missing level") {
        val event = LogCodec.decode("1693232661802 <topic> <localhost> <123> <test> tes")
        event.env shouldBe "test"
        event.level shouldBe Level.Info
    }

    test("missing tag") {
        val event = LogCodec.decode("1693232661802L <topic> <local-hostname> <1234> <dev> info (Te")
        event.tag shouldBe "Unknown"
        event.tagMap.shouldBeEmpty()
        event.message.shouldBeEmpty()
    }

    test("invalid tag maps") {
        val event = LogCodec.decode("1693232661802 <topic> <localhost> <123> <test> debug (TestTag) [key:valu")
        event.tagMap.shouldBeEmpty()
        event.tag shouldBe "TestTag"
    }

    test("missing message") {
        val event = LogCodec.decode("1693232661802L <topic> <local-hostname> <1234> <dev> info (TestTag) [key:value]  -")
        event.tagMap["key"] shouldBe "value"
        event.message.shouldBeEmpty()
    }
})
