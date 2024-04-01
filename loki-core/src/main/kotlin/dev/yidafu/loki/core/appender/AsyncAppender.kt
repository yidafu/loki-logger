package dev.yidafu.loki.core.appender

import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentLinkedDeque

/**
 * push [dev.yidafu.loki.core.ILogEvent] to [queue] first. then per 16ms will consume all queue data.
 *
 */
abstract class AsyncAppender<E> : BaseAppender<E>() {
    private val queue: ConcurrentLinkedDeque<E> = ConcurrentLinkedDeque()

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

    override fun flush() {
        // 确保同步刷日志
        runBlocking {
            flushLog()
        }
    }

    private suspend fun flushLog() {
//        val iter = queue.iterator()
        val bufferArray = ArrayList<E>(20)
        while (queue.isEmpty()) {
            queue.poll()?.let {
                if (bufferArray.size == 20) {
                    asyncAppend(bufferArray)
                    bufferArray.clear()
                }
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
                flushLog()
            }
        }
    }
}
