package dev.yidafu.loki.core.reporter

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.io.File

/**
 * report log meta data
 */
@Serializable
data class LogMeta(
    /**
     * log file path
     */
    val filepath: String,
    /**
     * log file inode
     */
    val inode: Long,
    /**
     * pointer of already report content
     *
     */
    var pointer: Long,
    /**
     * @hide
     */
    val isDone: Boolean = false,
    /**
     * File instance of [filepath]
     */
    @Transient
    val logFile: File = File(filepath),
) {
    fun isReport(): Boolean {
        return logFile.isFile && pointer < logFile.length()
    }
}
