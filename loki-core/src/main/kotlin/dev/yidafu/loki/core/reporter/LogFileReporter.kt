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
import java.nio.file.StandardWatchEventKinds.*

class LogFileReporter(
    private val logDirectory: String,
    private val reportInterval: Long,

    private val codec: ICodec<ILogEvent> = LogCodec,
    private val sender: Sender,
) : Reporter {

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
            .map { it as LokiLogEvent }
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
        if (isStarted()) return

        _started = true
        initMetaFile()
        intervalJob = CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                delay(reportInterval)
                if (!isStarted()) break
                metaFile.getNeedReportFiles().forEach {
                    val stream = getStream(it)
                    val logs = stream.readLines()

                    // 不管上报成功与否，都需要自增
                    it.pointer += logs.fold(logs.size) { acc, s -> acc + s.length }

                    try {
                        report(logs)
                    } catch (tr: Throwable) {
                        println("send data to Loki failed ${tr.message}")
                    }
                }
                metaFile.updateMateFile()
            }
        }
    }

    override fun isStarted(): Boolean {
        return _started
    }

    /**
     * reporter stop 时会释放文件 fd，需要调用 [onStart] 重新初始化
     */
    override fun onStop() {
        if (!isStarted()) return

        _started = false
        intervalJob?.cancel()
        close()
    }

    override fun setEventBus(bus: EventBus) {
        bus.addListener(this)
    }

    /**
     * 关闭持有的文件
     */
    private fun close() {
        logStreamMap.values.forEach { it.close() }
        logStreamMap.clear()
        metaFile.close()
    }
}
