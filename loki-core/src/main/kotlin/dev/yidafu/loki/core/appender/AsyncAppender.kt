package dev.yidafu.loki.core.appender

import dev.yidafu.loki.core.list.LinkedRingList
import kotlinx.coroutines.*

abstract class AsyncAppender<E> : BaseAppender<E>() {
    private val queue: LinkedRingList<E> = LinkedRingList(1024)

    private var eventLoopJob: Job? = null
    override fun onStart() {
        super.onStart()
        startEventLoop()
    }

    override fun onStop() {
        super.onStop()
        eventLoopJob?.cancel()
        eventLoopJob = null
    }

    abstract fun filterLevel(event: E): Boolean

    private fun add(event: E) {
        queue.add(event)
    }

    override fun doAppend(event: E) {
        if (filterLevel(event)) {
            add(event)
        }
    }

    abstract suspend fun asyncAppend(eventArray: ArrayList<E>)

    private fun startEventLoop() {
        eventLoopJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                if (!isStarted()) {
                    break
                }
                delay(16)
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
        }
    }
}
