package dev.yidafu.loki.core

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.lang.IllegalArgumentException

class LevelTest : FunSpec({
    test("level compare") {
        (Level.Debug < Level.Info) shouldBe true
        (Level.Info < Level.Warn) shouldBe true
        (Level.Warn < Level.Error) shouldBe true
    }

    test("Level to int") {
        Level.Info.toInt() shouldBe Level.INFO_INT
    }

    test("Level from string") {
        Level.from("off") shouldBe Level.Off
        Level.from("trace") shouldBe Level.Trace
        Level.from("debug") shouldBe Level.Debug
        Level.from("info") shouldBe Level.Info
        Level.from("warn") shouldBe Level.Warn
        Level.from("error") shouldBe Level.Error
        Level.from("all") shouldBe Level.All
        shouldThrow<IllegalArgumentException> { Level.from("test") }
    }

    test("Level from int") {
        Level.from(0) shouldBe Level.Trace
        Level.from(10) shouldBe Level.Debug
        Level.from(20) shouldBe Level.Info
        Level.from(30) shouldBe Level.Warn
        Level.from(40) shouldBe Level.Error
        Level.from(Int.MIN_VALUE) shouldBe Level.Off
        Level.from(Int.MAX_VALUE) shouldBe Level.All

        shouldThrow<IllegalArgumentException> { Level.from(999) }
    }
})
