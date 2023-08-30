package dev.yidafu.loki.core.initializer

import org.slf4j.ILoggerFactory

class ContextInitializer(
    private val loggerFactory: ILoggerFactory,
) {
    /**
     * TODO: Config
     */
    fun autoConfig() {
        loggerFactory
    }
}
