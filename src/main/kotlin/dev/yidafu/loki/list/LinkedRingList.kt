package dev.yidafu.loki.list

class LinkedRingList<E>(override val size: Int) : Queue<E> {
    class Node<E>(
        internal var item: E?,
        internal var prev: Node<E>? = null,
        internal var next: Node<E>? = null,
    )

    inner class ListIterator(private var cursor: Node<E>) : MutableIterator<E> {
        /**
         * Returns `true` if the iteration has more elements.
         */
        override fun hasNext(): Boolean {
            return cursor != getTail()
        }

        /**
         * Returns the next element in the iteration.
         */
        override fun next(): E {
            val next = cursor.next!!
            val item = next.item
            cursor = next
            return item!!
        }

        /**
         * Removes from the underlying collection the last element returned by this iterator.
         */
        override fun remove() {
            val prev = cursor.prev!!
            this@LinkedRingList.removeNode(cursor)
            cursor = prev
        }
    }

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
        if (tail.next == head) { // 说明 List 满了，需要丢弃最老的数据
            head.item = null
            head = head.next!!
        }
        val tNext = tail.next!!
        tNext.item = e
        tail = tNext
        return true
    }

    /**
     * Inserts the specified element into this queue if it is possible to do so
     * immediately without violating capacity restrictions, returning
     * `true` upon success and throwing an `IllegalStateException`
     * if no space is currently available.
     *
     * @param e the element to add
     * @return `true` (as specified by [Collection.add])
     * @throws IllegalStateException if the element cannot be added at this
     * time due to capacity restrictions
     * @throws ClassCastException if the class of the specified element
     * prevents it from being added to this queue
     * @throws NullPointerException if the specified element is null and
     * this queue does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     * prevents it from being added to this queue
     */
    override fun add(e: E): Boolean {
        return enqueue(e)
    }

    /**
     * Inserts the specified element into this queue if it is possible to do
     * so immediately without violating capacity restrictions.
     * When using a capacity-restricted queue, this method is generally
     * preferable to [.add], which can fail to insert an element only
     * by throwing an exception.
     *
     * @param e the element to add
     * @return `true` if the element was added to this queue, else
     * `false`
     * @throws ClassCastException if the class of the specified element
     * prevents it from being added to this queue
     * @throws NullPointerException if the specified element is null and
     * this queue does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     * prevents it from being added to this queue
     */
    override fun offer(e: E): Boolean {
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
        val first = head.next!!.item!!
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
        val first = head.next!!.item!!
        head = head.next!!
        return first
    }

    /**
     * Retrieves, but does not remove, the head of this queue.  This method
     * differs from [peek][.peek] only in that it throws an exception
     * if this queue is empty.
     *
     * @return the head of this queue
     * @throws NoSuchElementException if this queue is empty
     */
    override fun element(): E {
        if (isEmpty()) throw NoSuchElementException()
        return head.next!!.item!!
    }

    /**
     * Retrieves, but does not remove, the head of this queue,
     * or returns `null` if this queue is empty.
     *
     * @return the head of this queue, or `null` if this queue is empty
     */
    override fun peek(): E? {
        if (isEmpty()) return null
        return head.next!!.item!!
    }

    /**
     * Removes all elements from this collection.
     */
    override fun clear() {
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
        return head == tail
    }

    override fun iterator(): MutableIterator<E> {
        return ListIterator(head)
    }

    /**
     * Retains only the elements in this collection that are contained in the specified collection.
     *
     * @return `true` if any element was removed from the collection, `false` if the collection was not modified.
     */
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
