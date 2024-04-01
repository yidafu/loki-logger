package dev.yidafu.loki.core.appender

import dev.yidafu.loki.core.listener.EventListener

interface Appender<E> : EventListener {
    abstract var name: String

    fun doAppend(event: E)

    fun flush()
}
