package dev.yidafu.loki.android

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import org.slf4j.LoggerFactory

@SuppressLint("StaticFieldLeak")
object YLog {
    internal var context: Context? = null

    fun setContext(ctx: Context) {
        context = ctx
    }

    fun v(tag: String, msg: String): Int {
        return println(
            Log.VERBOSE,
            tag,
            msg,
        )
    }

    fun v(tag: String, msg: String, tr: Throwable?): Int {
        return println(
            Log.VERBOSE,
            tag,
            "$msg\n${Log.getStackTraceString(tr)}",
        )
    }

    fun d(tag: String, msg: String): Int {
        return println(Log.DEBUG, tag, msg)
    }

    fun d(tag: String, msg: String, tr: Throwable?): Int {
        return println(
            Log.DEBUG,
            tag,
            "$msg\n${Log.getStackTraceString(tr)}",
        )
    }

    fun i(tag: String, msg: String): Int {
        return println(
            Log.INFO,
            tag,
            msg,
        )
    }

    fun i(tag: String, msg: String, tr: Throwable?): Int {
        return println(Log.INFO, tag, "$msg\n${Log.getStackTraceString(tr)}")
    }
    fun w(tag: String, msg: String): Int {
        return println(
            Log.WARN,
            tag,
            msg,
        )
    }

    fun w(tag: String, msg: String, tr: Throwable): Int {
        return println(
            Log.WARN,
            tag,
            "$msg\n${Log.getStackTraceString(tr)}",
        )
    }

    fun w(tag: String, tr: Throwable): Int {
        return println(
            Log.WARN,
            tag,
            Log.getStackTraceString(tr),
        )
    }

    fun e(tag: String, msg: String): Int {
        return println(
            Log.ERROR,
            tag,
            msg,
        )
    }

    fun e(tag: String, msg: String, tr: Throwable?): Int {
        return println(
            Log.ERROR,
            tag,
            "$msg\n${Log.getStackTraceString(tr)}",
        )
    }

    private fun println(priority: Int, tag: String, msg: String): Int {
        LoggerFactory.getLogger(tag).atLevel(fromLogInt(priority)).log(msg)
        return 0
    }
}
