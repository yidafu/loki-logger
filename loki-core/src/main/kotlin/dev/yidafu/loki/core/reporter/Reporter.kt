package dev.yidafu.loki.core.reporter

import dev.yidafu.loki.core.listener.EventListener

interface Reporter : EventListener {

    fun report(logList: List<String>)
}
