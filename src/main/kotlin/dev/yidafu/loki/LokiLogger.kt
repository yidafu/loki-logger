package dev.yidafu.loki

import org.slf4j.Marker
import org.slf4j.event.Level
import org.slf4j.helpers.AbstractLogger

class LokiLogger(private val _name: String) : AbstractLogger() {

    override fun getName(): String {
        return _name
    }

    /**
     * Is the logger instance enabled for the TRACE level?
     *
     * @return True if this Logger is enabled for the TRACE level,
     * false otherwise.
     * @since 1.4
     */
    override fun isTraceEnabled(): Boolean {
        return true
    }

    /**
     * Similar to [.isTraceEnabled] method except that the
     * marker data is also taken into account.
     *
     * @param marker The marker data to take into consideration
     * @return True if this Logger is enabled for the TRACE level,
     * false otherwise.
     *
     * @since 1.4
     */
    override fun isTraceEnabled(marker: Marker?): Boolean {
        return true
    }

    /**
     * Is the logger instance enabled for the DEBUG level?
     *
     * @return True if this Logger is enabled for the DEBUG level,
     * false otherwise.
     */
    override fun isDebugEnabled(): Boolean {
        return true
    }

    /**
     * Similar to [.isDebugEnabled] method except that the
     * marker data is also taken into account.
     *
     * @param marker The marker data to take into consideration
     * @return True if this Logger is enabled for the DEBUG level,
     * false otherwise.
     */
    override fun isDebugEnabled(marker: Marker?): Boolean {
        return true
    }

    /**
     * Is the logger instance enabled for the INFO level?
     *
     * @return True if this Logger is enabled for the INFO level,
     * false otherwise.
     */
    override fun isInfoEnabled(): Boolean {
        return true
    }

    /**
     * Similar to [.isInfoEnabled] method except that the marker
     * data is also taken into consideration.
     *
     * @param marker The marker data to take into consideration
     * @return true if this Logger is enabled for the INFO level,
     * false otherwise.
     */
    override fun isInfoEnabled(marker: Marker?): Boolean {
        return true
    }

    /**
     * Is the logger instance enabled for the WARN level?
     *
     * @return True if this Logger is enabled for the WARN level,
     * false otherwise.
     */
    override fun isWarnEnabled(): Boolean {
        return true
    }

    /**
     * Similar to [.isWarnEnabled] method except that the marker
     * data is also taken into consideration.
     *
     * @param marker The marker data to take into consideration
     * @return True if this Logger is enabled for the WARN level,
     * false otherwise.
     */
    override fun isWarnEnabled(marker: Marker?): Boolean {
        return true
    }

    /**
     * Is the logger instance enabled for the ERROR level?
     *
     * @return True if this Logger is enabled for the ERROR level,
     * false otherwise.
     */
    override fun isErrorEnabled(): Boolean {
        return true
    }

    /**
     * Similar to [.isErrorEnabled] method except that the
     * marker data is also taken into consideration.
     *
     * @param marker The marker data to take into consideration
     * @return True if this Logger is enabled for the ERROR level,
     * false otherwise.
     */
    override fun isErrorEnabled(marker: Marker?): Boolean {
        return true
    }

    override fun getFullyQualifiedCallerName(): String {
        return "ev.yidafu.loki.LokiLogger"
    }

    /**
     * Given various arguments passed as parameters, perform actual logging.
     *
     *
     * This method assumes that the separation of the args array into actual
     * objects and a throwable has been already operated.
     *
     * @param level the SLF4J level for this event
     * @param marker  The marker to be used for this event, may be null.
     * @param messagePattern The message pattern which will be parsed and formatted
     * @param arguments  the array of arguments to be formatted, may be null
     * @param throwable  The exception whose stack trace should be logged, may be null
     */
    override fun handleNormalizedLoggingCall(
        level: Level?,
        marker: Marker?,
        messagePattern: String?,
        arguments: Array<out Any>?,
        throwable: Throwable?,
    ) {
        println("[Loki] $level ${String.format(messagePattern ?: "", *(arguments ?: arrayOf()))}")
    }
}
