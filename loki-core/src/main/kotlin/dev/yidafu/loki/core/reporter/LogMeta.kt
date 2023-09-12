package dev.yidafu.loki.core.reporter

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.io.File


@Serializable
data class LogMeta(
    val filepath: String,
    val inode: Long,
    var pointer: Long,
    val isDone: Boolean = false,
    @Transient
    val logFile: File = File(filepath),
) {
    fun isReport(): Boolean {
        return  logFile.isFile && pointer < logFile.length()
    }
}
