package dev.yidafu.loki.core.appender

import java.util.concurrent.CopyOnWriteArrayList

class AppenderAttachableImpl<E> : AppenderAttachable<E> {
    private val appenderList = CopyOnWriteArrayList<Appender<E>>()

    override fun addAppender(appender: Appender<E>) {
        appenderList.addIfAbsent(appender)
    }

    override fun getAppender(name: String): Appender<E> {
        return appenderList.find { it.name == name } ?: throw IllegalStateException("appender $name not found")
    }

    override fun detachAndStopAllAppenders() {
        appenderList.forEach { it.onStop() }
        appenderList.clear()
    }

    override fun detachAppender(name: String): Boolean {
        val appender = appenderList.find { it.name == name }
        return appender?.let {
            it.onStop()
            appenderList.remove(it)
            true
        } ?: false
    }

    override fun detachAppender(appender: Appender<E>): Boolean {
        return appenderList.remove(appender)
    }

    override fun isAttached(appender: Appender<E>): Boolean {
        return appenderList.any { it == appender }
    }

    override fun iterator(): Iterator<Appender<E>> {
        return appenderList.iterator()
    }
}
