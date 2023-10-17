package dev.yidafu.loki.core.appender.cleaner

import io.kotest.common.runBlocking
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import java.io.File

class LazyCleanerTest : FunSpec({
    val dir = "/tmp/log/lazy-cleaner-test"
    beforeEach {
        File(dir).mkdirs()
    }
    test("FixedSurvivalTimeCleaner") {
        File("$dir/test1.log").writeText("update modify date")
        val cleaner = LazyCleaner(dir, 100)
        runBlocking {
            cleaner.clean()
            File(dir).list().size shouldBe 1
            delay(1500)
            cleaner.clean()
            File(dir).list().size shouldBe 1
        }
    }
})
