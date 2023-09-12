package dev.yidafu.loki.core.sender

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class LokiStreamTest : FunSpec({
    test("LokiStream to JSON String") {
        val str = LokiStream(mapOf("key" to "value"), arrayOf(arrayOf("1694521198868", "log message"))).toString()

        str shouldBe "{\"stream\":{\"key\":\"value\"},\"values\":[[\"1694521198868\",\"log message\"]]}"
    }

    test("escape chars: \\\", \n \t ") {
        val str = LokiStream(mapOf("k\"ey" to "value"), arrayOf(arrayOf("1694521198868", "\tlog\nmessage"))).toString()
        str shouldBe "{\"stream\":{\"k\\\"ey\":\"value\"},\"values\":[[\"1694521198868\",\"\\tlog\\nmessage\"]]}"

    }
})