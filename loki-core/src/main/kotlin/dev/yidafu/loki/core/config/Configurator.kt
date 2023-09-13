package dev.yidafu.loki.core.config

import com.charleskorn.kaml.Yaml
import dev.yidafu.loki.core.Constants
import dev.yidafu.loki.core.LokiLoggerContext
import dev.yidafu.loki.core.utils.ResourceUtils
import kotlinx.serialization.decodeFromString

class Configurator(
    private val loggerFactory: LokiLoggerContext,
) {
    private val configFilePath: String by lazy {
        System.getProperty(Constants.CONFIG_FILE_PROPERTY, Constants.DEFAULT_CONFIG_FILENAME)
    }

    /**
     * read config file in resources/loki.yaml
     */
    fun autoConfig() {
        val configContext = ResourceUtils.readResource(configFilePath)

        val config = Yaml.default.decodeFromString<Configuration>(configContext)
        loggerFactory.config = config
    }
}
