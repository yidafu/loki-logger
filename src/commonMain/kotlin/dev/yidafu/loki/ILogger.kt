package dev.yidafu.loki

interface ILogger {
    fun trace(message: String)

    fun info(message: String)

    fun warn(message: String)

    fun error(message: String)
}
