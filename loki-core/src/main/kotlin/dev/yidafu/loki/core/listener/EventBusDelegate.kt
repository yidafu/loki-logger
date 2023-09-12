package dev.yidafu.loki.core.listener

object EventBusDelegate : EventBus {
    private val listenerList = mutableListOf<EventListener>()
    override fun addListener(listener: EventListener) {
        listenerList.add(listener)
    }

    override fun removeListener(listener: EventListener) {
        listenerList.remove(listener)
    }

    override fun emitStart() {
        listenerList.forEach { it.onStart() }
    }

    override fun emitStop() {
        listenerList.forEach { it.onStop() }
    }
}
