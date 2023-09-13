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

    test("multiple labels and values") {
        val str = LokiStream(
            mapOf("key1" to "value1", "key2" to "value2"),
            arrayOf(
                arrayOf("1694521198868", "log message 1"),
                arrayOf("1694521198868", "log message 2"),
                arrayOf("1694521198868", "log message 3"),
            ),
        ).toString()
        str shouldBe "{\"stream\":{\"key1\":\"value1\",\"key2\":\"value2\"},\"values\":[[\"1694521198868\",\"log message 1\"],[\"1694521198868\",\"log message 2\"],[\"1694521198868\",\"log message 3\"]]}"
    }
})
