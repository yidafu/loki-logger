package dev.yidafu.loki.core.utils

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import java.io.File
import java.nio.file.Files

class FileUtilsTest : FunSpec({
    beforeEach {
        File("/tmp/loki-logger/file-utils").mkdirs()
    }

    test("FileUtils#getINode") {
        File("/tmp/loki-logger/file-utils").mkdirs()
        val f = File("/tmp/loki-logger/file-utils/inode.txt")
            f.writeText("x")
        FileUtils.getINode(f) shouldBeGreaterThan  0
    }

    test("get inode throw UnsupportedOperationException") {
        mockkStatic(Files::getAttribute)
        every { Files.getAttribute(any(), any()) } answers {
            throw UnsupportedOperationException()
        }
        val f = File("/tmp/loki-logger/file-utils/inode.txt")
        f.writeText("x")
        FileUtils.getINode(f) shouldBe  f.absoluteFile.hashCode()
    }
})