package dev.yidafu.loki.core.reporter

import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

/**
 * log file input stream
 */
class LogFileInputStream(
    private val file: File,
    offset: Long,
    private val bufferedSize: Int = 8 * 1024,
    private val fileInputStream: FileInputStream = FileInputStream(file),
    private val inputStream: BufferedInputStream = BufferedInputStream(fileInputStream, bufferedSize),
) : InputStream() {

    private val lineBuilder = StringBuilder()

    init {
        inputStream.skip(offset)
    }

    override fun read(): Int {
        return inputStream.read()
    }

    override fun read(b: ByteArray, off: Int, len: Int): Int {
        return inputStream.read(b, off, len)
    }

    /**
     * read all lines that recently added to log file
     */
    fun readLines(): List<String> {
        var char: Char
        val lines = mutableListOf<String>()
        while (read().also { char = it.toChar() } != -1) {
            if (char == '\n') {
                val line = lineBuilder.toString()
                lines.add(line)
                lineBuilder.clear()
            } else {
                lineBuilder.append(char)
            }
        }

        return lines
    }

    override fun close() {
        inputStream.close()
    }
}
