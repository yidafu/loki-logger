package dev.yidafu.loki.core.sender

interface Sender {
    fun send(data: ByteArray): Boolean
}