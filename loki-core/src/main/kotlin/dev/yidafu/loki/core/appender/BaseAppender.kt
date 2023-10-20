package dev.yidafu.loki.core.appender

import dev.yidafu.loki.core.ILogEvent
import dev.yidafu.loki.core.codec.ICodec
import dev.yidafu.loki.core.listener.EventBus

/**
 * implement [dev.yidafu.loki.core.listener.EventListener] interface
 */
abstract class BaseAppender<E> : Appender<E> {
    private var _isStarted = false

    abstract var encoder: ICodec<ILogEvent>
    override fun isStarted(): Boolean = _isStarted

    override fun onStart() {
        _isStarted = true
    }

    override fun onStop() {
        _isStarted = false
    }

    /**
     * nop flush
     */
    override fun flush() {}

    override fun setEventBus(bus: EventBus) {
        bus.addListener(this)
    }
}
