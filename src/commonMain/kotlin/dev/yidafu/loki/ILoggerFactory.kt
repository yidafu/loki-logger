package dev.yidafu.loki

interface ILoggerFactory {
    fun createLogger(labelGroup: LabelGroup): ILogger
}
