package dev.yidafu.loki.core

import dev.yidafu.loki.core.config.Configurator
import org.slf4j.ILoggerFactory
import org.slf4j.IMarkerFactory
import org.slf4j.helpers.BasicMarkerFactory
import org.slf4j.spi.MDCAdapter
import org.slf4j.spi.SLF4JServiceProvider

open class BaseServiceProvider : SLF4JServiceProvider {
    protected lateinit var loggerContext: LokiLoggerContext
    protected lateinit var markerFactory: BasicMarkerFactory
    protected lateinit var mdcAdapter: LokiMDCAdapter

    private val REQUESTED_API_VERSION = "2.99.99"

    /**
     * Return the instance of [ILoggerFactory] that
     * [org.slf4j.LoggerFactory] class should bind to.
     *
     * @return instance of [ILoggerFactory]
     */
    override fun getLoggerFactory(): ILoggerFactory {
        return loggerContext
    }

    /**
     * Return the instance of [IMarkerFactory] that
     * [org.slf4j.MarkerFactory] class should bind to.
     *
     * @return instance of [IMarkerFactory]
     */
    override fun getMarkerFactory(): IMarkerFactory {
        return markerFactory
    }

    /**
     * Return the instance of [MDCAdapter] that
     * [org.slf4j.MDC] should bind to.
     *
     * @return instance of [MDCAdapter]
     */
    override fun getMDCAdapter(): MDCAdapter {
        return mdcAdapter
    }

    /**
     * Return the maximum API version for SLF4J that the logging
     * implementation supports.
     *
     *
     * For example: `"2.0.1"`.
     *
     * @return the string API version.
     */
    override fun getRequestedApiVersion(): String {
        return REQUESTED_API_VERSION
    }

    private fun initializeLoggerContext() {
        Configurator(loggerContext).autoConfig()
    }

    /**
     * Initialize the logging back-end.
     *
     *
     * **WARNING:** This method is intended to be called once by
     * [org.slf4j.LoggerFactory] class and from nowhere else.
     *
     */
    override fun initialize() {
        loggerContext = LokiLoggerContext()
        mdcAdapter = LokiMDCAdapter()
        markerFactory = BasicMarkerFactory()

        initializeLoggerContext()
    }
}
