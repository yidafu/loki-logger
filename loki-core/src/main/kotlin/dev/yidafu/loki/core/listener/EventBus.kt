package dev.yidafu.loki.core.listener

/**
 * EventBus
 */
interface EventBus {
    fun addListener(listener: EventListener)

    fun removeListener(listener: EventListener)

    fun emitStart()

    fun emitStop()
}
