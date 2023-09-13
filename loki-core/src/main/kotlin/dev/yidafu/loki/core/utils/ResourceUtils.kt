package dev.yidafu.loki.core.utils

import java.io.File

internal object ResourceUtils {
    fun readResource(name: String): String {
        val url = ResourceUtils::class.java.classLoader.getResource(name)
        requireNotNull(url) {
            "Resource $name File not exit"
        }
        return File(url.path).readText(Charsets.UTF_8)
    }
}
