package dev.yidafu.loki.core.appender.naming

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.types.shouldBeInstanceOf

class NamingStrategyFactoryTest: FunSpec({
    test("return default strategy if name incorrectly") {
        NamingStrategyFactory.getStrategy("unknown").shouldBeInstanceOf<DateFileNamingStrategy>()
    }
})