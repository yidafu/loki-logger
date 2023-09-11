package dev.yidafu.loki.core.utils

import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.util.*


object FileUtils {
    @Suppress("NewApi")
    @Throws(IOException::class)
    fun getINode(file: File): Long /* , SecurityException */ {
        return try {
            val inode: Any? = Files.getAttribute(file.toPath(), "unix:ino")
            if (inode is Long) inode else file.absoluteFile.hashCode().toLong()
        } catch (e: UnsupportedOperationException) {
            // getting an inode is unsupported for this JVM or that filesystem
            return file.absoluteFile.hashCode().toLong()
        } catch (e: IllegalArgumentException) {
            return file.absoluteFile.hashCode().toLong()
        }
    }
}