package dev.yidafu.loki.core.appender

import dev.yidafu.loki.core.ILogEvent
import dev.yidafu.loki.core.codec.ICodec
import dev.yidafu.loki.core.codec.LogCodec
import java.io.PrintStream

class ConsoleAppender(override var name: String) : SyncAppender<ILogEvent>() {
    override var encoder: ICodec<ILogEvent> = LogCodec

    private val outputStream: PrintStream = System.out

    override fun writeOut(bytes: ByteArray) {
        outputStream.writeBytes(bytes)
        outputStream.write('\n'.code)
        outputStream.flush()
    }
}
