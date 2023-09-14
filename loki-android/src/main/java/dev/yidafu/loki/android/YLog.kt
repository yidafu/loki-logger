package dev.yidafu.loki.android

import android.content.Context

object YLog {
    internal var context: Context? = null

    fun setContext(ctx: Context) {
        context = ctx
    }
}
