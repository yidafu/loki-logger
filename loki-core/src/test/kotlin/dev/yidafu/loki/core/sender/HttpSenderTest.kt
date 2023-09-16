package dev.yidafu.loki.core.sender

import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.mockserver.MockServerListener
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse

class HttpSenderTest : FunSpec({
    listener(MockServerListener(3000))

    beforeTest {
        MockServerClient("localhost", 3000).`when`(
            HttpRequest.request("/loki/api/v1/push")
                .withMethod("POST")
                .withHeader("Content-Type", "application/json")
                .withBody("{\"streams\":[{\"stream\":{\"tag\":\"map\"},\"values\":[[\"1694833118526\",\"log message\"]]}]}"),
        ).respond(
            HttpResponse.response().withStatusCode(200).withBody("ok"),
        )
    }

    test("seed log data to loki") {
        val sender = HttpSender("http://localhost:3000/loki/api/v1/push")
        val streams = LokiSteams(
            arrayOf(
                LokiStream(
                    mapOf("tag" to "map"),
                    arrayOf(
                        arrayOf("1694833118526", "log message"),
                    ),
                ),
            ),
        )
        println(streams)
        sender.send(
            streams.toString().toByteArray(),
        ).shouldBeTrue()
    }

    test("loki server not exists") {
        val sender = HttpSender("http://localhost:3001/loki/api/v1/push")
        val streams = LokiSteams(
            arrayOf(
                LokiStream(
                    mapOf("tag" to "map"),
                    arrayOf(
                        arrayOf("1694833118526", "log message"),
                    ),
                ),
            ),
        )
        println(streams)
        sender.send(
            streams.toString().toByteArray(),
        ).shouldBeFalse()
    }
})
