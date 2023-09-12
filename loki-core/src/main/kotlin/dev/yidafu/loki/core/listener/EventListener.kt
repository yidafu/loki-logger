package dev.yidafu.loki.core.listener

interface EventListener {
    fun onStart()

    fun isStarted(): Boolean

    fun onStop()

    fun setEventBus(bus: EventBus)
}
