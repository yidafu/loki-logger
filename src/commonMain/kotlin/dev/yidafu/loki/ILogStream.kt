package dev.yidafu.loki

import kotlinx.datetime.Clock

interface ILogStream {
    fun child(): ILogStream

    fun log(timestamps: Long, message: String)

    fun log(message: String) {
        log(Clock.System.now().toEpochMilliseconds(), message)
    }
}
