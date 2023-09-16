package dev.yidafu.loki.core.utils

import java.io.File

internal object ResourceUtils {
    fun readResource(name: String): String? {
        val url = ResourceUtils::class.java.classLoader.getResource(name) ?: return null
        return File(url.path).readText(Charsets.UTF_8)
    }
}
