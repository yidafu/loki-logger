package dev.yidafu.loki.core.appender

import dev.yidafu.loki.core.list.LinkedRingList
import kotlinx.coroutines.*

abstract class AsyncAppender<E> : BaseAppender<E>() {
    private val queue: LinkedRingList<E> = LinkedRingList(1024)

    private var _isStarted = false
    private var eventLoopJob: Job? = null
    override fun start() {
        super.start()
        startEventLoop()
    }

    override fun stop() {
        super.stop()
        eventLoopJob?.cancel()
        eventLoopJob = null
    }

    override fun isStarted(): Boolean = _isStarted
    abstract fun filterLevel(event: E): Boolean

    private fun add(event: E) {
        queue.add(event)
    }

    override fun doAppend(event: E) {
        if (filterLevel(event)) {
            add(event)
        }
    }

    abstract fun asyncAppend(event: E)

    private fun startEventLoop() {
        eventLoopJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                if (!_isStarted) {
                    break
                }
                delay(16)
                val iter = queue.iterator()
                while (iter.hasNext()) {
                    val event = iter.next()
                    asyncAppend(event)
                    iter.remove()
                }
            }
        }
    }
}
