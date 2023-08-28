package dev.yidafu.loki.core.hook

interface StartableHook {
    fun start()

    fun isStarted(): Boolean

    fun stop()
}
