package dev.yidafu.loki.core.reporter

import dev.yidafu.loki.core.listener.EventBus
import kotlinx.coroutines.*

abstract class IntervalReporter(private val reportInterval: Long) : Reporter {

    private var _started = false

    private var intervalJob: Job? = null

    override fun onStart() {
        if (isStarted()) return

        _started = true
        intervalJob = CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                delay(reportInterval)
                if (!isStarted()) break
                report()
            }
        }
    }

    override fun isStarted(): Boolean {
        return _started
    }

    /**
     * reporter stop 时会释放文件 fd，需要调用 [onStart] 重新初始化
     */
    override fun onStop() {
        if (!isStarted()) return

        _started = false
        intervalJob?.cancel()
    }

    override fun setEventBus(bus: EventBus) {
        bus.addListener(this)
    }
}
