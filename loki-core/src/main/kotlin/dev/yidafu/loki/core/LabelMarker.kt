package dev.yidafu.loki.core

import org.slf4j.Marker
import java.util.concurrent.CopyOnWriteArrayList

class LabelMarker(
    private val key: String,
    private val value: String,
) : Marker {

    private val referenceList = CopyOnWriteArrayList<LabelMarker>()

    /**
     * Get the name of this Marker.
     *
     * @return name of marker
     */
    override fun getName(): String {
        return key
    }

    /**
     * Add a reference to another Marker.
     *
     *
     * Note that the fluent API allows adding multiple markers to a logging statement.
     * It is often preferable to use multiple markers instead of nested markers.
     *
     *
     * @param reference
     * a reference to another marker
     * @throws IllegalArgumentException
     * if 'reference' is null
     */
    override fun add(reference: Marker?) {
        if (reference == null) {
            throw IllegalArgumentException("A null value cannot be added to a Marker as reference.")
        }
        if (reference !is LabelMarker) {
            throw IllegalArgumentException("reference must be LabelMarker.")
        }
        if (!contains(reference)) {
            if (!reference.contains(this)) {
                referenceList.add(reference)
            }
        }
    }

    /**
     * Remove a marker reference.
     *
     * @param reference
     * the marker reference to remove
     * @return true if reference could be found and removed, false otherwise.
     */
    override fun remove(reference: Marker?): Boolean {
        return referenceList.remove(reference)
    }

    @Deprecated("Deprecated in Java", ReplaceWith("hasReferences()"))
    override fun hasChildren(): Boolean {
        return hasReferences()
    }

    /**
     * Does this marker have any references?
     *
     * @return true if this marker has one or more references, false otherwise.
     */
    override fun hasReferences(): Boolean {
        return referenceList.size > 0
    }

    /**
     * Returns an Iterator which can be used to iterate over the references of this
     * marker. An empty iterator is returned when this marker has no references.
     *
     * @return Iterator over the references of this marker
     */
    override fun iterator(): MutableIterator<Marker> {
        return referenceList.iterator()
    }

    /**
     * Does this marker contain a reference to the 'other' marker? Marker A is defined
     * to contain marker B, if A == B or if B is referenced by A, or if B is referenced
     * by any one of A's references (recursively).
     *
     * @param other
     * The marker to test for inclusion.
     * @throws IllegalArgumentException
     * if 'other' is null
     * @return Whether this marker contains the other marker.
     */
    override fun contains(other: Marker?): Boolean {
        if (other == null) {
            throw IllegalArgumentException("other cannot be null")
        }
        if (this == other) {
            return true
        }
        if (hasReferences()) {
            for (marker in this.iterator()) {
                if (this == marker) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * Does this marker contain the marker named 'name'?
     *
     * If 'name' is null the returned value is always false.
     *
     * @param name The marker name to test for inclusion.
     * @return Whether this marker contains the other marker.
     */
    override fun contains(name: String?): Boolean {
        if (name == null) {
            throw IllegalArgumentException("name cannot be null")
        }
        if (getName() == name) {
            return true
        }
        if (hasReferences()) {
            for (marker in this.iterator()) {
                if (marker.contains(name)) {
                    return true
                }
            }
        }
        return false
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        if (other is Marker) {
            return name == other.name
        }
        return false
    }

    override fun toString(): String {
        return StringBuilder().apply {
            append("{ ")
            append("$name: $value")
            if (hasReferences()) {
                append(", ")
            }
            val iter = this@LabelMarker.iterator()
            while (iter.hasNext()) {
                val label = iter.next()
                if (label is LabelMarker) {
                    append("${label.name}: ${label.value}")
                }
                if (iter.hasNext()) {
                    append(", ")
                }
            }
            append(" }")
        }.toString()
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }
}
