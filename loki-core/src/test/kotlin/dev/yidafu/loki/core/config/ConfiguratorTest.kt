package dev.yidafu.loki.core.config

import dev.yidafu.loki.core.Constants
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ConfiguratorTest : FunSpec({
    test("auto configure") {
        val config = Configurator().autoConfig()
        config.topic shouldBe "test-app"
        config.logDirectory shouldBe "/test/log/loki"
        config.httpEndpoint shouldBe "http://test.com/loki/api/v1/push"
        config.namingStrategy shouldBe "date"
        config.reportInterval shouldBe 1000
    }

    test("change default config filename") {
        System.setProperty(Constants.CONFIG_FILE_PROPERTY, "test-loki.yaml")
        val config = Configurator().autoConfig()
        config.topic shouldBe "unknown"
    }
})
