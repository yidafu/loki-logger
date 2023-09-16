package dev.yidafu.loki.core.reporter

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainInOrder
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardOpenOption

class LogFileInputStreamTest : FunSpec({
    fun File.appendText(text: String) {
        Files.write(this.toPath(), text.toByteArray(), StandardOpenOption.APPEND)
    }
    test("LogFileInputStream#readLines") {
        val dirPath = "/tmp/loki-logger/input-stream"
        val dir = File(dirPath)
        dir.mkdirs()
        File("$dirPath/test.log").delete()
        val log = File("$dirPath/test.log")
        log.writeText("#")
        val stream = LogFileInputStream(log, 0)
        log.appendText("first line")
        stream.readLines().shouldBeEmpty()
        log.appendText("\nsecond line\n")
        stream.readLines() shouldContainInOrder listOf("#first line", "second line")
        stream.close()
    }
})