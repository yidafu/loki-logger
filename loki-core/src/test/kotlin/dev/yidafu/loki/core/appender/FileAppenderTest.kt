package dev.yidafu.loki.core.appender

import dev.yidafu.loki.core.Level
import dev.yidafu.loki.core.LokiLogEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import kotlin.test.Test

class FileAppenderTest {
    @Test
    fun testFileLock() {
        val file = File("test.txt")
        val fos = FileOutputStream(file, true)
        val os = BufferedOutputStream(fos)
        val fl = fos.channel.lock()
        os.write("Suppressed: kotlinx.coroutines.internal.DiagnosticCoroutineContextException: [CoroutineId(2)".toByteArray())
        os.write("\n".toByteArray())
        os.flush()
        fl.release()

        val fl2 = fos.channel.lock()

        os.write("Suppressed: kotlinx.coroutines.internal.DiagnosticCoroutineContextException: [CoroutineId(2)".toByteArray())
        os.flush()
        fl2.release()
    }

    @Test
    fun testFileAppender() {
        runBlocking {
            val appender = FileAppender()
            appender.onStart()
            val event = LokiLogEvent(
                1693232661802L,
                "topic",
                "local-hostname",
                "1234",
                "dev",
                Level.Info,
                "TestTag",
                mapOf("key" to "value", "key2" to "value2"),
                "Suppressed: kotlinx.coroutines.internal.DiagnosticCoroutineContextException: [CoroutineId(2), \"coroutine#2\":StandaloneCoroutine{Cancelling}@72ebba7b, Dispatchers.IO]",
            )

            repeat(100) {
                appender.doAppend(event)
            }
            delay(1000)
            println("stop appender")
            appender.onStop()
        }

    }
}