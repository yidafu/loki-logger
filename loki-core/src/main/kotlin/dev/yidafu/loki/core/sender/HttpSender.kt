package dev.yidafu.loki.core.sender

import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL

class HttpSender(private var endpoint: String) : Sender {
    override fun send(data: ByteArray): Boolean {
        val url = URL(endpoint)
        try {
            val conn = url.openConnection() as HttpURLConnection
            with(conn) {
                requestMethod = "POST"
                connectTimeout = 3000
                setRequestProperty("Content-length", data.size.toString())
                setRequestProperty("Content-Type", "application/json")
                doInput = true
                doOutput = true
                val out = DataOutputStream(outputStream)
                out.write(data)
                out.flush()
            }
            return conn.responseCode == HttpURLConnection.HTTP_OK
        } catch (e: Throwable) {
            println(e.printStackTrace())
        }
        return false
    }
}