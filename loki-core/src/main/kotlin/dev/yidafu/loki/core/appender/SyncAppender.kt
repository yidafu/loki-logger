package dev.yidafu.loki.core.appender

import dev.yidafu.loki.core.ILogEvent

abstract class SyncAppender<E : ILogEvent> : BaseAppender<E>() {

    abstract fun writeOut(bytes: ByteArray)
    override fun doAppend(event: E) {
        writeOut(encoder.encode(event).toByteArray())
    }
}
