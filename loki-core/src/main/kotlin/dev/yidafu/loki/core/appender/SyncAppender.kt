package dev.yidafu.loki.core.appender

import dev.yidafu.loki.core.ILogEvent

abstract class SyncAppender<E : ILogEvent> : BaseAppender<E>() {

    abstract fun writeout(bytes: ByteArray)
    override fun doAppend(event: E) {
        writeout(encoder.encode(event).toByteArray())
    }
}
