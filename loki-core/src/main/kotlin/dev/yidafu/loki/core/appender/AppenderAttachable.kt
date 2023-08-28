package dev.yidafu.loki.core.appender

interface AppenderAttachable<E> {
    fun addAppender(appender: Appender<E>)

    fun getAppender(name: String): Appender<E>

    fun isAttached(appender: Appender<E>): Boolean

    fun detachAndStopAllAppenders()

    fun detachAppender(appender: Appender<E>): Boolean

    fun detachAppender(name: String): Boolean

    fun iterator(): Iterator<Appender<E>>
}
