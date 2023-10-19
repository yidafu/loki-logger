package dev.yidafu.loki.core

import dev.yidafu.loki.core.appender.AppenderAttachable
import dev.yidafu.loki.core.appender.AppenderAttachableImpl
import org.slf4j.Marker
import org.slf4j.helpers.AbstractLogger
import org.slf4j.helpers.MessageFormatter

/**
 * LokiLogger extends [org.slf4j.helpers.AbstractLogger].
 */
class LokiLogger(
    private val _name: String,
    private val parent: LokiLogger? = null,
    private val children: MutableList<LokiLogger> = mutableListOf(),
    private var logLevel: Level = Level.Info,
) : AbstractLogger(),
    AppenderAttachable<ILogEvent> by AppenderAttachableImpl()
{
    var level: Level
        get() = logLevel
        set(value) {
            logLevel = value
        }

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
        return logLevel <= Level.Trace
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
        return logLevel <= Level.Debug
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
        return logLevel <= Level.Info
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
        return logLevel <= Level.Warn
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
        return logLevel <= Level.Error
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
        return "dev.yidafu.loki.core.LokiLogger"
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
        level: org.slf4j.event.Level?,
        marker: Marker?,
        messagePattern: String?,
        arguments: Array<out Any>?,
        throwable: Throwable?,
    ) {
        val message = MessageFormatter.arrayFormat(messagePattern, arguments, throwable).message
        val event = buildLoggingEvent(Level.from(level?.toInt() ?: Level.INFO_INT), _name, message)
        lookupAndCallAppender(event)
    }

    private fun callAppenderList(event: ILogEvent) {
        for (appender in iterator()) {
            appender.doAppend(event)
        }
    }

    private fun lookupAndCallAppender(event: ILogEvent) {
        var logger: LokiLogger? = this
        while (logger != null) {
            logger.callAppenderList(event)
            logger = logger.parent
        }
    }

    private fun buildLoggingEvent(level: Level, loggerName: String, message: String): ILogEvent {
        return LokiLogEvent.create(level, loggerName, message)
    }

    internal fun getChildByName(childName: String): LokiLogger? {
        return children.find { it.getName() == childName }
    }

    internal fun createChildByName(childName: String): LokiLogger {
        val childLogger = LokiLogger(childName, this, logLevel = logLevel)
        children.add(childLogger)
        return childLogger
    }

    companion object {
        /**
         * root logger name.
         */
        const val ROOT_LOGGER_NAME = "@ROOT@"
    }
}
