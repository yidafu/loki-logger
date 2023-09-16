package dev.yidafu.loki.core.config

import com.charleskorn.kaml.Yaml
import dev.yidafu.loki.core.Constants
import dev.yidafu.loki.core.utils.ResourceUtils
import kotlinx.serialization.decodeFromString

class Configurator() {
    private val configFilePath: String by lazy {
        System.getProperty(Constants.CONFIG_FILE_PROPERTY, Constants.DEFAULT_CONFIG_FILENAME)
    }

    /**
     * read config file in resources/loki.yaml
     */
    fun autoConfig(): Configuration {
        val configStr = ResourceUtils.readResource(configFilePath)

        return if (configStr != null) {
            Yaml.default.decodeFromString<Configuration>(configStr)
        } else {
            Configuration()
        }
    }
}
