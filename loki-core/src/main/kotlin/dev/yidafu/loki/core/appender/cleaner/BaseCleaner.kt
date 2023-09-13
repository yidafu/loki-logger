package dev.yidafu.loki.core.appender.cleaner

import dev.yidafu.loki.core.listener.EventBus
import dev.yidafu.loki.core.listener.EventListener
import java.util.*

/**
 *
 * @param checkInterval 大于 0 就会定时清理，小于 0 不会设置定时任务
 */
abstract class BaseCleaner(private val checkInterval: Long) : Cleaner, EventListener {
    private var timer: Timer? = null
    override fun onStart() {
        if (checkInterval > 0) {
            timer = Timer()
            timer?.schedule(
                object : TimerTask() {
                    override fun run() {
                        clean()
                    }
                },
                checkInterval,
            )
        }
    }

    override fun isStarted(): Boolean {
        return timer != null
    }
    override fun onStop() {
        timer?.cancel()
        timer = null
    }

    override fun setEventBus(bus: EventBus) {
    }
}
