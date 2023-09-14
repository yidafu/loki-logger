package dev.yidafu.loki.core

import org.slf4j.spi.MDCAdapter
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class LokiMDCAdapter : MDCAdapter {
    private val contextMap = ConcurrentHashMap<String, String>()

    /**
     * Put a context value (the `val` parameter) as identified with
     * the `key` parameter into the current thread's context map.
     * The `key` parameter cannot be null. The `val` parameter
     * can be null only if the underlying implementation supports it.
     *
     *
     * If the current thread does not have a context map it is created as a side
     * effect of this call.
     */
    override fun put(key: String?, value: String?) {
        requireNotNull(key) { "key cannot be null" }
        requireNotNull(value) { "value cannot be null" }

        contextMap[key] = value
    }

    /**
     * Get the context identified by the `key` parameter.
     * The `key` parameter cannot be null.
     *
     * @return the string value identified by the `key` parameter.
     */
    override fun get(key: String?): String? {
        requireNotNull(key) { "key cannot be null" }

        return contextMap[key]
    }

    /**
     * Remove the context identified by the `key` parameter.
     * The `key` parameter cannot be null.
     *
     *
     *
     * This method does nothing if there is no previous value
     * associated with `key`.
     */
    override fun remove(key: String?) {
        requireNotNull(key) { "key cannot be null" }

        contextMap.remove(key)
    }

    /**
     * Clear all entries in the MDC.
     */
    override fun clear() {
        contextMap.clear()
    }

    /**
     * Return a copy of the current thread's context map, with keys and
     * values of type String. Returned value may be null.
     *
     * @return A copy of the current thread's context map. May be null.
     * @since 1.5.1
     */
    override fun getCopyOfContextMap(): MutableMap<String, String> {
        return contextMap.toMutableMap()
    }

    /**
     * Set the current thread's context map by first clearing any existing
     * map and then copying the map passed as parameter. The context map
     * parameter must only contain keys and values of type String.
     *
     * Implementations must support null valued map passed as parameter.
     *
     * @param contextMap must contain only keys and values of type String
     *
     * @since 1.5.1
     */
    override fun setContextMap(contextMap: MutableMap<String, String>) {
        clear()
        contextMap.forEach { this.contextMap[it.key] = it.value }
    }

    /**
     * Push a value into the deque(stack) referenced by 'key'.
     *
     * @param key identifies the appropriate stack
     * @param value the value to push into the stack
     * @since 2.0.0
     */
    override fun pushByKey(key: String?, value: String?) {
        TODO("Not yet implemented")
    }

    /**
     * Pop the stack referenced by 'key' and return the value possibly null.
     *
     * @param key identifies the deque(stack)
     * @return the value just popped. May be null/
     * @since 2.0.0
     */
    override fun popByKey(key: String?): String {
        TODO("Not yet implemented")
    }

    /**
     * Returns a copy of the deque(stack) referenced by 'key'. May be null.
     *
     * @param key identifies the stack
     * @return copy of stack referenced by 'key'. May be null.
     *
     * @since 2.0.0
     */
    override fun getCopyOfDequeByKey(key: String?): Deque<String>? {
        TODO("Not yet implemented")
    }

    /**
     * Clear the deque(stack) referenced by 'key'.
     *
     * @param key identifies the  stack
     *
     * @since 2.0.0
     */
    override fun clearDequeByKey(key: String?) {
        TODO("Not yet implemented")
    }
}
