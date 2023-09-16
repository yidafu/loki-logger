package dev.yidafu.loki.core.listener

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

class EventBusTest : FunSpec({
    test("Event Bus delegate") {
        class TestBus : EventBus by EventBusDelegate()

        val bus = TestBus()
        val listener = object : EventListener {
            var started = false
            override fun onStart() {
                started = true
            }

            override fun isStarted(): Boolean {
                return started
            }

            override fun onStop() {
                started = false
            }

            override fun setEventBus(bus: EventBus) {
                bus.addListener(this)
            }
        }
        listener.setEventBus(bus)

        listener.isStarted().shouldBeFalse()
        bus.emitStart()
        listener.isStarted().shouldBeTrue()
        bus.emitStop()
        listener.isStarted().shouldBeFalse()

        bus.removeListener(listener)
        bus.emitStart()
        listener.isStarted().shouldBeFalse()
    }
})
