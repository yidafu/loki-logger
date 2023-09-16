package dev.yidafu.loki.core

/**
 * ILogEvent
 */
interface ILogEvent {
    /**
     * timestamp
     */
    abstract val timestamp: String

    /**
     * topic as app name
     */
    abstract val topic: String

    /**
     * hostname or machine id
     */
    abstract val hostname: String

    /**
     * process id
     */
    abstract val pid: String

    /**
     * app env
     */
    abstract val env: String

    /**
     * log level
     */
    abstract val level: Level

    /**
     * logger tag name
     */
    abstract val tag: String

    /**
     * other tags
     */
    abstract val tagMap: Map<String, String>

    /**
     * message content
     */
    abstract val message: String

}
