package dev.yidafu.loki.core.appender

import dev.yidafu.loki.core.ILogEvent
import dev.yidafu.loki.core.codec.ICodec
import dev.yidafu.loki.core.hook.StartableHook

abstract class BaseAppender<E> : Appender<E>, StartableHook {
    private var _isStarted = false

    abstract var encoder: ICodec<ILogEvent>
    override fun isStarted(): Boolean = _isStarted

    override fun start() {
        _isStarted = true
    }

    override fun stop() {
        _isStarted = false
    }
}
