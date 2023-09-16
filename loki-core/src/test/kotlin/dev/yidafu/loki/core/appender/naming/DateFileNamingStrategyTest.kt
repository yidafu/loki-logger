package dev.yidafu.loki.core.appender.naming

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class DateFileNamingStrategyTest : FunSpec({
    test("generate file name") {
        val strategy = DateFileNamingStrategy()
        strategy.generate(0, 1693526400000L) shouldBe "2023-09-01"
    }
})
