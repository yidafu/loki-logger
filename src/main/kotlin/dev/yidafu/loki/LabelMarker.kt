package dev.yidafu.loki

import org.slf4j.Marker

class LabelMarker : Marker {
    /**
     * Get the name of this Marker.
     *
     * @return name of marker
     */
    override fun getName(): String {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    /**
     * Remove a marker reference.
     *
     * @param reference
     * the marker reference to remove
     * @return true if reference could be found and removed, false otherwise.
     */
    override fun remove(reference: Marker?): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasChildren(): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Does this marker have any references?
     *
     * @return true if this marker has one or more references, false otherwise.
     */
    override fun hasReferences(): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Returns an Iterator which can be used to iterate over the references of this
     * marker. An empty iterator is returned when this marker has no references.
     *
     * @return Iterator over the references of this marker
     */
    override fun iterator(): MutableIterator<Marker> {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }
}