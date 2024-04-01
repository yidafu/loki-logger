package dev.yidafu.loki.android

import android.util.Log
import org.slf4j.event.Level

fun fromLogInt(level: Int): Level {
    return when (level) {
        Log.VERBOSE -> Level.TRACE
        Log.DEBUG -> Level.DEBUG
        Log.INFO -> Level.INFO
        Log.WARN -> Level.WARN
        Log.ERROR -> Level.ERROR
        else -> Level.INFO
    }
}
