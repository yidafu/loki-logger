package dev.yidafu.loki.core

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldNotBeEqual
import io.kotest.matchers.maps.shouldContainAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockkStatic
import org.slf4j.MDC

class LokiLogEventTest : FunSpec({
    fun getMap(): MutableMap<String, String> {
        return mutableMapOf(
            "topic" to "app",
            "hostname" to "localhost",
            "pid" to "123",
            "level" to "info",
            "env" to "test",
            "tag" to "TagName",
            "custom" to "value",
        )
    }

    test("create LokiLogEvent") {
        mockkStatic(MDC::getCopyOfContextMap)
        every { MDC.getCopyOfContextMap() } returns getMap()
        val event = LokiLogEvent.create(Level.Info, "LokiLogEvent", "log message")
        event.tag shouldBe "LokiLogEvent"
        event.message shouldBe "log message"
        event.level shouldBe Level.Info
        event.getMap() shouldContainAll getMap()
    }

    test("LokiLogEvent Equality") {
        mockkStatic(MDC::getCopyOfContextMap)
        every { MDC.getCopyOfContextMap() } returns getMap()

        val event1 = LokiLogEvent.create(Level.Info, "LokiLogEvent", "log message")
        event1 shouldNotBeEqual "not LokiLogEvent"
        val event2 = LokiLogEvent.create(Level.Info, "LokiLogEvent", "log message")
        event1 shouldBeEqual event1
        event1.uniqueKey shouldBe event2.uniqueKey
        event1 shouldNotBeEqual event2

        val event3 = LokiLogEvent.create(Level.Info, "LokiLogEvent2", "log message")
        event1.uniqueKey shouldNotBe event3.uniqueKey
        event1 shouldNotBeEqual event3
    }
})
