package dev.yidafu.loki.core.reporter

import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.nio.ByteBuffer
import java.io.DataOutputStream

import java.io.ByteArrayOutputStream




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

    private val byteList = mutableListOf<Byte>()

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
     * return new line string only when '\n' arised
     */
    fun readLines(): List<String> {
        var char: Byte
        val lines = mutableListOf<String>()
        while (read().also { char = it.toByte() } != -1) {
            if (char == '\n'.toByte()) {
                val line = String(byteList.toByteArray(), Charsets.UTF_8)
                lines.add(line)
                byteList.clear()
            } else {
                byteList.add(char)
            }
        }

        return lines
    }

    override fun close() {
        inputStream.close()
    }
}
