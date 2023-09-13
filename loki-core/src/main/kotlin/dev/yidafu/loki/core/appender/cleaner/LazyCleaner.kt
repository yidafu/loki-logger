package dev.yidafu.loki.core.appender.cleaner

/**
 *
 */
class LazyCleaner(override val logDir: String = "") : BaseCleaner(-1) {
    override fun clean() {
    }
}
