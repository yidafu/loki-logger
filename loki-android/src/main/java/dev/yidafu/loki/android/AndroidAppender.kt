package dev.yidafu.loki.android

import dev.yidafu.loki.core.Level
import android.Log
import dev.yidafu.loki.core.ILogEvent
import dev.yidafu.loki.core.appender.BaseAppender
import dev.yidafu.loki.core.codec.ICodec
import dev.yidafu.loki.core.codec.LogCodec

fun Level.toLogInt(): Int {
    return when (this) {
        is Level.Debug -> Log.INFO
        Level.All -> Log.DEBUG
        Level.Error -> Log.ERROR
        Level.Info -> Log.INFO
        Level.Off -> Log.ERROR
        Level.Trace -> Log.VERBOSE
        Level.Warn -> Log.WARN
    }
}

class AndroidAppender(
    override var name: String = "ANDROID",
    override var encoder: ICodec<ILogEvent> = LogCodec
) : BaseAppender<ILogEvent>() {
    override fun doAppend(event: ILogEvent) {
        Log.println(event.level.toLogInt(), event.loggerName, event.message)
    }
}
