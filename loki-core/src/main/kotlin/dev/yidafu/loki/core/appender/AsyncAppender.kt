package dev.yidafu.loki.core.appender

import dev.yidafu.loki.core.list.LinkedRingList
import kotlinx.coroutines.*

/**
 * push [dev.yidafu.loki.core.ILogEvent] to [queue] first. then per 16ms will consume all queue data.
 *
 */
abstract class AsyncAppender<E> : BaseAppender<E>() {
    private val queue: LinkedRingList<E> = LinkedRingList(1024)

    private var eventLoopJob: Job? = null
    override fun onStart() {
        super.onStart()
        startEventLoop()
    }

    override fun onStop() {
        CoroutineScope(Dispatchers.IO).launch {
            flush()
        }
        eventLoopJob?.cancel()
        eventLoopJob = null
        super.onStop()
    }

    /**
     * TODO: [event] need filter
     */
    abstract fun filterLevel(event: E): Boolean

    private fun add(event: E) {
        queue.add(event)
    }

    override fun doAppend(event: E) {
        if (filterLevel(event)) {
            add(event)
        }
    }
    private suspend fun flush() {
        val iter = queue.iterator()
        val bufferArray = ArrayList<E>(20)
        while (iter.hasNext()) {
            val event = iter.next()
            bufferArray.add(event)
            iter.remove()
            if (bufferArray.size == 20) {
                asyncAppend(bufferArray)
                bufferArray.clear()
            }
        }
        if (bufferArray.isNotEmpty()) {
            asyncAppend(bufferArray)
            bufferArray.clear()
        }
    }

    /**
     * async append eventArray
     */
    abstract suspend fun asyncAppend(eventArray: ArrayList<E>)

    private fun startEventLoop() {
        eventLoopJob = CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                if (!isStarted()) {
                    break
                }
                delay(16)
                flush()
            }
        }
    }
}
