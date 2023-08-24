package dev.yidafu.loki

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LabelMarkerTest {
    @Test
    fun testCreateMarker() {
        val l1 = LabelMarker("env", "dev")
        assertEquals(l1.toString(), "{ env: dev }")
        assertFalse { l1.hasReferences() }

        l1.add(LabelMarker("os", "linux"))
        assertEquals(l1.toString(), "{ env: dev, os: linux }")

        l1.add(LabelMarker("pid", "123"))

        assertTrue { l1.contains("pid") }
        assertTrue { l1.hasReferences() }
        assertEquals(l1.toString(), "{ env: dev, os: linux, pid: 123 }")

    }
}
