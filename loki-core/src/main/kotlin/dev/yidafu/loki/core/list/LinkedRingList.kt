package dev.yidafu.loki.core.list

import java.util.Queue

/**
 * Linked Ring List
 * if queue is full, queue will drop oldest item.
 */
internal class LinkedRingList<E>(
    override val size: Int,
    private var _length: Int = 0,
) : Queue<E> {
    class Node<E>(
        internal var item: E?,
        internal var prev: Node<E>? = null,
        internal var next: Node<E>? = null,
    )

    private var head: Node<E> = Node(null, null, null)
    private var tail: Node<E> = head

    init {
        require(size > 0) { "LinkedRingList size must greater then 0" }
        var node = head
        for (i in 1..size) {
            val newNode = Node(null, node)
            node.next = newNode
            node = newNode
        }
        head.prev = node
        node.next = head
    }

    val length: Int
        get() = _length
    private fun getTail(): Node<E> {
        return tail
    }

    /**
     * Checks if the specified element is contained in this collection.
     */
    override fun contains(element: E): Boolean {
        return false
    }

    /**
     * Checks if all elements in the specified collection are contained in this collection.
     */
    override fun containsAll(elements: Collection<E>): Boolean {
        return false
    }

    /**
     * Adds all of the elements of the specified collection to this collection.
     *
     * @return `true` if any of the specified elements was added to the collection, `false` if the collection was not modified.
     */
    override fun addAll(elements: Collection<E>): Boolean {
        elements.forEach { add(it) }

        return true
    }

    private fun enqueue(e: E): Boolean {
        head.item = e
        val tNext = tail.next
        if (tNext != null) {
            tail = tNext
            tNext.item = e
            if (tNext == head) {
                val hNext = head.next
                if (hNext != null) {
                    hNext.item = null
                    head = hNext
                }
            }
            return true
        }
        return false
    }

    override fun add(e: E): Boolean {
        _length += 1
        return enqueue(e)
    }

    override fun offer(e: E): Boolean {
        _length += 1
        return enqueue(e)
    }

    /**
     * Retrieves and removes the head of this queue.  This method differs
     * from [poll()][.poll] only in that it throws an exception if
     * this queue is empty.
     *
     * @return the head of this queue
     * @throws NoSuchElementException if this queue is empty
     */
    override fun remove(): E {
        if (isEmpty()) throw NoSuchElementException()
        _length -= 1
        val first = head.next?.item ?: throw NoSuchElementException()
        head = head.next!!
        return first
    }

    /**
     * Retrieves and removes the head of this queue,
     * or returns `null` if this queue is empty.
     *
     * @return the head of this queue, or `null` if this queue is empty
     */
    override fun poll(): E? {
        if (isEmpty()) return null
        _length -= 1
        val first = head.next?.item
        head = head.next!!
        return first
    }

    override fun element(): E {
        if (isEmpty()) throw NoSuchElementException()
        return head.next?.item ?: throw NoSuchElementException()
    }

    override fun peek(): E? {
        if (isEmpty()) return null
        return head.next?.item
    }

    override fun clear() {
        _length = 0
        var start = head.next
        while (start != null && start != tail) {
            start.item = null
            start = start.next
        }
        tail = head.next!!
    }

    /**
     * Returns `true` if the collection is empty (contains no elements), `false` otherwise.
     */
    override fun isEmpty(): Boolean {
        return head == tail.next
    }

    override fun iterator(): MutableIterator<E> {
        TODO()
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        return true
    }

    private fun removeNode(node: Node<E>): Boolean {
        node.item = null
        if (node == head) {
            head = head.next!!
        } else if (node == tail) {
            tail = node.prev!!
        } else {
            val prev = node.prev!!
            val next = node.next!!
            prev.next = next
            next.prev = prev
//            node = prev

            val tNext = tail.next
            tail.next = node
            node.next = tNext

            node.prev = tail
            tNext!!.prev = node
        }
        _length -= 1
        return true
    }

    /**
     * Removes a single instance of the specified element from this
     * collection, if it is present.
     *
     * @return `true` if the element has been successfully removed; `false` if it was not present in the collection.
     */
    override fun remove(element: E): Boolean {
        throw NotImplementedError("LinkedRingList#removeAll(element: E) would not implement")
    }

    /**
     * Removes all of this collection's elements that are also contained in the specified collection.
     *
     * @return `true` if any of the specified elements was removed from the collection, `false` if the collection was not modified.
     */
    override fun removeAll(elements: Collection<E>): Boolean {
        throw NotImplementedError("LinkedRingList#removeAll(elements: Collection<E>) would not implement")
    }
}
