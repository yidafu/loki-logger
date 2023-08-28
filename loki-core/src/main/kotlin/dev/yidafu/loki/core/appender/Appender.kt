package dev.yidafu.loki.core.appender

import dev.yidafu.loki.core.hook.StartableHook

interface Appender<E> : StartableHook {
    abstract var name: String

    fun doAppend(event: E)
}
