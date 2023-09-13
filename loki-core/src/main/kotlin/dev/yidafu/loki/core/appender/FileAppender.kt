package dev.yidafu.loki.core.appender

import dev.yidafu.loki.core.ILogEvent
import dev.yidafu.loki.core.appender.cleaner.FixedSurvivalTimeCleaner
import dev.yidafu.loki.core.appender.naming.FileNamingStrategy
import dev.yidafu.loki.core.appender.naming.NamingStrategyFactory
import dev.yidafu.loki.core.codec.ICodec
import dev.yidafu.loki.core.codec.LogCodec
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

class FileAppender(
    private val logDir: String,
    namingStrategyName: String,
    maxSurvivalTime: Long = TimeUnit.DAYS.toDays(7),
    cleanerCheckInterval: Long = 5.minutes.toLong(DurationUnit.MILLISECONDS),
    override var name: String = "FILE",
    override var encoder: ICodec<ILogEvent> = LogCodec,
    private val namingStrategy: FileNamingStrategy = NamingStrategyFactory.getStrategy(namingStrategyName),
) : AsyncAppender<ILogEvent>() {
    private var outputStream: LogFileOutputStream? = null

    private val cleaner = FixedSurvivalTimeCleaner(logDir, maxSurvivalTime, cleanerCheckInterval)

    private val logFilePath: String
        get() = "$logDir/${namingStrategy.generate(0, System.currentTimeMillis())}.log"

    override fun onStart() {
        super.onStart()
        cleaner.onStart()
        val logFile = File(logFilePath)
        outputStream = LogFileOutputStream(logFile)
    }

    override fun onStop() {
        outputStream?.flush()
        outputStream?.close()
        cleaner.onStop()
        super.onStop()
    }

    override fun filterLevel(event: ILogEvent): Boolean {
        return true
    }

    override suspend fun asyncAppend(eventArray: ArrayList<ILogEvent>) {
        val evtStr = eventArray.joinToString("\n") { encoder.encode(it) } + "\n"

        outputStream?.write(evtStr.toByteArray())
    }
}
