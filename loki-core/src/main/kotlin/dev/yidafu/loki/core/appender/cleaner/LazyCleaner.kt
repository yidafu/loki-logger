package dev.yidafu.loki.core.appender.cleaner

/**
 * Nop implement
 */
class LazyCleaner(override val logDir: String = "") : BaseCleaner(-1) {
    override fun clean() {
    }
}
