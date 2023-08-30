package dev.yidafu.loki.core.appender

import dev.yidafu.loki.core.ILogEvent
import dev.yidafu.loki.core.codec.ICodec
import dev.yidafu.loki.core.codec.LogCodec
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class FileAppender(
    override var name: String = "FILE",
    override var encoder: ICodec<ILogEvent> = LogCodec,
) : AsyncAppender<ILogEvent>() {
    private val filenamePrefix = "loki_"

//    private var logFile: File? = null
    private var outputStream: LogFileOutputStream? = null
    override fun onStart() {
        super.onStart()
        val logFile = File("${filenamePrefix}_1.log")
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

    override suspend fun asyncAppend(eventList: ArrayList<ILogEvent>) {
        println("asyncAppend ${System.currentTimeMillis()}")
        val evtStr = eventList.joinToString("\n") { encoder.encode(it) } + "\n"

        outputStream?.write(evtStr.toByteArray())
    }
}