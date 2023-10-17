package dev.yidafu.loki.core.appender.cleaner

import io.kotest.common.runBlocking
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import java.io.File

class FixedSurvivalTimeCleanerTest : FunSpec({
    beforeEach {
        File("/tmp/log/cleaner-test").mkdirs()
    }
    test("FixedSurvivalTimeCleaner") {
        File("/tmp/log/cleaner-test/test1.log").writeText("update modify date")
        val cleaner = FixedSurvivalTimeCleaner("/tmp/log/cleaner-test", 1000, 100)
        runBlocking {
            cleaner.clean()
            File("/tmp/log/cleaner-test").list().size shouldBe 1
            delay(1500)
            cleaner.clean()
            File("/tmp/log/cleaner-test").list().size shouldBe 0
        }
    }
})
