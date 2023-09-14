package dev.yidafu.loki.core.list

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.test.assertEquals

class LinkedRingListTest : FunSpec({

    test("size 1 list") {
        val list = LinkedRingList<Int>(1)
        list.add(1)
        list.add(2)
        list.add(3)
        list.peek() shouldBe 3
    }

    test("add and remove item on list") {

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

    test("poll operate") {
        val list = LinkedRingList<Int>(2)
        list.add(1)
        list.add(2)
        list.add(3)
        list.poll() shouldBe 2
        list.poll() shouldBe 3
        list.isEmpty() shouldBe true
    }

    test("list iterator") {

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

    test("one item list iterate") {
        val list = LinkedRingList<Int>(12)
        list.add(1)
        list.fold(0) { acc, i -> acc + i } shouldBe 1
        list.remove()
        list.add(2)
        list.fold(0) { acc, i -> acc + i } shouldBe 2
    }
})
