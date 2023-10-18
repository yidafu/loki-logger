package dev.yidafu.loki.core.reporter

import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.StandardCharsets

/**
 * log file input stream
 */
class LogFileInputStream(
    private val file: File,
    offset: Long,
    bufferedSize: Int = 8 * 1024,
    fileInputStream: FileInputStream = FileInputStream(file),
    streamReader: InputStreamReader = InputStreamReader(fileInputStream, StandardCharsets.UTF_8),
    private val bufferedReader: BufferedReader = BufferedReader(streamReader, bufferedSize),
) : Reader() {

    init {
        bufferedReader.skip(offset)
    }

    override fun read(p0: CharArray?, p1: Int, p2: Int): Int {
        return bufferedReader.read(p0, p1, p2)
    }

    override fun close() {
        bufferedReader.close()
    }
}
