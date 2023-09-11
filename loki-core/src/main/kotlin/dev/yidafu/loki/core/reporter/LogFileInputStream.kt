package dev.yidafu.loki.core.reporter

import java.io.*

class LogFileInputStream(
    private val file: File,
    private val offset: Long,
): InputStream() {
    private val BUFFERED_SIZE = 8 * 1024;
    private val fileInputStream = FileInputStream(file)
    private val steamReader = InputStreamReader(fileInputStream)
    private val bufferedRead = BufferedReader(steamReader, BUFFERED_SIZE)
    /**
     * Reads the next byte of data from the input stream. The value byte is
     * returned as an `int` in the range `0` to
     * `255`. If no byte is available because the end of the stream
     * has been reached, the value `-1` is returned. This method
     * blocks until input data is available, the end of the stream is detected,
     * or an exception is thrown.
     *
     *
     *  A subclass must provide an implementation of this method.
     *
     * @return     the next byte of data, or `-1` if the end of the
     * stream is reached.
     * @throws     [java.io.IOException]  if an I/O error occurs.
     */
    override fun read(): Int {
        return bufferedRead.read()
    }

    fun readLine(): String? {
        bufferedRead.skip(offset)
        return bufferedRead.readLine()
    }
}