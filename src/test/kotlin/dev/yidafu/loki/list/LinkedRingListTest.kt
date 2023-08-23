package dev.yidafu.loki.list

import kotlin.test.Test
import kotlin.test.assertEquals

class LinkedRingListTest {
    @Test
    fun testAddMethod() {
        val list = LinkedRingList<Int>(4)
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        assertEquals(list.fold(0) { acc, i -> acc + i }, 10)

        list.add(5)
        assertEquals(list.fold(0) { acc, i -> acc + i }, 14)
        list.remove()
        assertEquals(list.fold(0) { acc, i -> acc + i }, 12)
    }

    @Test
    fun testIterator() {
        val list = LinkedRingList<Int>(4)
        list.add(1)
        list.add(2)
        val iter = list.iterator()
        for (i in iter) {
            assertEquals(i, 1)
            iter.remove()
        }
    }

    @Test
    fun testArrayIterator() {
        val list = LinkedRingList<Int>(4)
        list.add(1)
        list.add(2)
        list.add(3)
        list.add(4)
        val iter = list.iterator()
        while (iter.hasNext()) {
            if (iter.next() % 2 == 0) iter.remove()
        }
        assertEquals(list.fold(0) { acc, i -> acc + i }, 4)
    }
}
