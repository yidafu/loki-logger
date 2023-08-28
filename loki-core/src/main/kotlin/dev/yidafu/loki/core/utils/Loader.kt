package dev.yidafu.loki.core.utils

object Loader {
    fun getResource(resource: String, classLoader: ClassLoader) {
        classLoader.getResource(resource)
    }
}
