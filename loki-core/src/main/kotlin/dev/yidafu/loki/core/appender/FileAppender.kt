package dev.yidafu.loki.core.appender

import dev.yidafu.loki.core.ILogEvent
import dev.yidafu.loki.core.appender.naming.DateFileNamingStrategy
import dev.yidafu.loki.core.appender.naming.FileNamingStrategy
import dev.yidafu.loki.core.appender.naming.NamingStrategyFactory
import dev.yidafu.loki.core.codec.ICodec
import dev.yidafu.loki.core.codec.LogCodec
import java.io.File

class FileAppender(
    private val logDir: String,
    namingStrategyName: String,
//    private val logFilePrefix: String,
    override var name: String = "FILE",
    override var encoder: ICodec<ILogEvent> = LogCodec,
    private val namingStrategy: FileNamingStrategy = NamingStrategyFactory.getStrategy(namingStrategyName),
) : AsyncAppender<ILogEvent>() {
    private var outputStream: LogFileOutputStream? = null

    private val logFilePath: String
        get() = "$logDir/${namingStrategy.generate(0, System.currentTimeMillis())}.log"

    override fun onStart() {
        super.onStart()
        val logFile = File(logFilePath)
        outputStream = LogFileOutputStream(logFile)
    }

    override fun onStop() {
        outputStream?.flush()
        outputStream?.close()
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
