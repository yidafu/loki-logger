package dev.yidafu.loki.core.reporter

import dev.yidafu.loki.core.listener.EventListener

/**
 * report log strings to server
 */
interface Reporter : EventListener {

    fun report(logList: List<String>)
    fun report()
}
