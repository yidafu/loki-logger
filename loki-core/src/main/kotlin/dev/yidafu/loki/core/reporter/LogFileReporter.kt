package dev.yidafu.loki.core.reporter

import dev.yidafu.loki.core.ILogEvent
import dev.yidafu.loki.core.LokiLogEvent
import dev.yidafu.loki.core.codec.ICodec
import dev.yidafu.loki.core.codec.LogCodec
import dev.yidafu.loki.core.listener.EventBus
import dev.yidafu.loki.core.sender.LokiSteams
import dev.yidafu.loki.core.sender.LokiStream
import dev.yidafu.loki.core.sender.Sender
import kotlinx.coroutines.*
import java.io.Closeable
import java.nio.file.StandardWatchEventKinds.*

class LogFileReporter(
    private val logDirectory: String,
    private val reportInterval: Long,

    private val codec: ICodec<ILogEvent> = LogCodec,
    private val sender: Sender
) : Reporter, Closeable {

    private lateinit var metaFile: LogMetaFile

    private var _started = false

    private var intervalJob: Job? = null

    private val logStreamMap = mutableMapOf<String, LogFileInputStream>()

    private fun getStream(logMeta: LogMeta): LogFileInputStream {
         return logStreamMap[logMeta.filepath] ?: run {
            val stream = LogFileInputStream(logMeta.logFile, logMeta.pointer)
            logStreamMap[logMeta.filepath] = stream
            stream
        }
    }

    override fun report(logList: List<String>) {
        if (logList.isEmpty()) return

        val streams = logList.map { codec.decode(it) }
            .map { it as LokiLogEvent}
            // 压缩 log
            .groupBy { it.uniqueKey }
            .values.map {
                val tagMap = it[0].getMap()
                val values = it.map { event ->
                    arrayOf(event.timestamp.toString(), event.message)
                }.toTypedArray()
                LokiStream(tagMap, values)
            }.toTypedArray()
        val lokiSteams = LokiSteams(streams)
        sender.send(lokiSteams.toString().toByteArray())
    }

    private fun initMetaFile() {
        metaFile = LogMetaFile(logDirectory)
    }

    override fun onStart() {
        _started = true
        initMetaFile()
        intervalJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                delay(reportInterval)
                if (!isStarted()) break
                metaFile.getNeedReportFiles().forEach {
                    val stream = getStream(it)
                    val logs = stream.readLines()

                    it.pointer += logs.fold(logs.size) { acc, s -> acc + s.length }
                    report(logs)
                }
                metaFile.updateMateFile()
            }

        }
    }

    override fun isStarted(): Boolean {
        return _started
    }

    override fun onStop() {
        _started = false
        intervalJob?.cancel()
        close()
    }

    override fun setEventBus(bus: EventBus) {
        bus.addListener(this)
    }

    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this
     * method has no effect.
     *
     *
     *  As noted in [AutoCloseable.close], cases where the
     * close may fail require careful attention. It is strongly advised
     * to relinquish the underlying resources and to internally
     * *mark* the `Closeable` as closed, prior to throwing
     * the `IOException`.
     *
     * @throws IOException if an I/O error occurs
     */
    override fun close() {
        logStreamMap.values.forEach { it.close() }
        metaFile.close()
    }
}