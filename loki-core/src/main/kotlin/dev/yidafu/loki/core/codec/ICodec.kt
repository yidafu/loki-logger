package dev.yidafu.loki.core.codec

interface ICodec<E> {
    fun encode(event: E): String

    fun decode(raw: String): E
}
