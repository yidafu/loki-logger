package dev.yidafu.loki.core.appender.cleaner

import java.io.File

/**
 * 清除策略：固定保存时间
 * 超过[maxSurvivalTime]ms的日志文件删除掉
 */
class FixedSurvivalTimeCleaner(
    override val logDir: String,
    private val maxSurvivalTime: Long,
    checkInterval: Long,
) : BaseCleaner(checkInterval) {
    override fun clean() {
        val dir = File(logDir)
        val currentTime = System.currentTimeMillis()
        if (dir.exists() && dir.isDirectory) {
            dir.listFiles()
                ?.filter { it.isFile && it.extension == "log" }
                ?.forEach {
                    val lastModified = it.lastModified()
                    if (currentTime - lastModified > maxSurvivalTime) {
                        it.delete()
                    }
                }
        }
    }
}
