package dev.yidafu.loki

import org.slf4j.ILoggerFactory
import org.slf4j.IMarkerFactory
import org.slf4j.helpers.BasicMDCAdapter
import org.slf4j.helpers.BasicMarkerFactory
import org.slf4j.spi.MDCAdapter
import org.slf4j.spi.SLF4JServiceProvider

class LokiServiceProvider : SLF4JServiceProvider {
    private var loggerFactory: ILoggerFactory? = null
    private val markerFactory = BasicMarkerFactory()
    private val mdcAdapter = BasicMDCAdapter()
    private val REQUESTED_API_VERSION = "2.0.99"

    /**
     * Return the instance of [ILoggerFactory] that
     * [org.slf4j.LoggerFactory] class should bind to.
     *
     * @return instance of [ILoggerFactory]
     */
    override fun getLoggerFactory(): ILoggerFactory {
        return loggerFactory!!
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
     * [MDC] should bind to.
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

    /**
     * Initialize the logging back-end.
     *
     *
     * **WARNING:** This method is intended to be called once by
     * [LoggerFactory] class and from nowhere else.
     *
     */
    override fun initialize() {
        loggerFactory = LokiLoggerFactory()
    }
}
