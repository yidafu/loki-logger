package dev.yidafu.loki.core.appender

import dev.yidafu.loki.core.ILogEvent
import dev.yidafu.loki.core.LokiLoggerContext
import dev.yidafu.loki.core.codec.ICodec

abstract class BaseAppender<E> : Appender<E> {
    private var _isStarted = false

    abstract var encoder: ICodec<ILogEvent>
    override fun isStarted(): Boolean = _isStarted

    override fun onStart() {
        _isStarted = true
    }

    override fun onStop() {
        _isStarted = false
        println("appender stopped")
    }

    fun setContext(context: LokiLoggerContext) {
        context.addListener(this)
    }
}
