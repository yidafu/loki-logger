package dev.yidafu.loki.core.appender

import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.nio.channels.FileLock

class LogFileOutputStream(private val file: File) : OutputStream() {
    private val outputStream: BufferedOutputStream
    private val fileOutputStream: FileOutputStream
    private val BUFFER_SIZE: Int = 8 * 1024
    private var pos: Int = 0
    init {
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        fileOutputStream = FileOutputStream(file, true)
        outputStream = BufferedOutputStream(fileOutputStream, BUFFER_SIZE)
    }

    override fun write(b: ByteArray) {
        safeWrite(b)
    }

    private fun safeWrite(buf: ByteArray) {
        val channel = fileOutputStream.channel
        var fileLock: FileLock? = null
        try {
            fileLock = channel.lock()
            if (buf.size <= BUFFER_SIZE) { // buf 小于 BUFFER_SIZE
                // 每次 flush 保存缓冲是满的(=BUFFER_SIZE),减少 flush 次数
                var bufIndex = 0
                do {
                    val restOSBufLen = BUFFER_SIZE - pos
                    val restBuffLen = buf.size - bufIndex
                    if (restOSBufLen >= restBuffLen) { // OutputStream 剩余 osBuf 能够放下 buf
                        val bufEndIndex = buf.size
                        val bufArr = buf.sliceArray(bufIndex..<bufEndIndex)
                        val bufLen = bufArr.size
                        write(bufArr, 0, bufLen)
                        pos += bufLen
                        bufIndex = bufEndIndex
                    } else {
                        val bufEndIndex = bufIndex + restOSBufLen
                        val bufArr = buf.sliceArray(bufIndex..<bufEndIndex)
                        val bufLen = bufArr.size
                        write(bufArr, 0, bufLen)
                        pos += bufLen
                        bufIndex = bufEndIndex
                    }

                    if (pos >= BUFFER_SIZE) {
                        flush()
                        pos = 0
                    }
                } while (bufIndex < buf.size - 1)
            } else {
                // 超过 BUFFER_SIZE 如果走上面缓冲逻辑会触发至少要2次 flush
                // 直接 write 只需要一次
                write(buf)
            }
        } catch (e: IOException) {
            println("LogFileOutputStream write byte array failed \n\t${e.stackTraceToString()}")
        } finally {
            fileLock?.release()
        }
    }
    override fun write(b: ByteArray, off: Int, len: Int) {
        outputStream.write(b, off, len)
    }

    /**
     * Writes the specified byte to this output stream. The general
     * contract for `write` is that one byte is written
     * to the output stream. The byte to be written is the eight
     * low-order bits of the argument `b`. The 24
     * high-order bits of `b` are ignored.
     *
     *
     * Subclasses of `OutputStream` must provide an
     * implementation for this method.
     *
     * @param b   the `byte`.
     * @throws [java.io.IOException]  if an I/O error occurs. In particular,
     * an `IOException` may be thrown if the
     * output stream has been closed.
     */
    override fun write(b: Int) {
        outputStream.write(b)
    }

    override fun close() {
        outputStream.close()
    }

    override fun flush() {
        outputStream.flush()
    }
}
