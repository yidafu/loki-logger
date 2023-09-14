package dev.yidafu.loki.core

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class LevelTest: FunSpec({
    test("level compare") {
        (Level.Debug < Level.Info) shouldBe true
        (Level.Info < Level.Warn) shouldBe true
        (Level.Warn < Level.Error) shouldBe true
    }
})