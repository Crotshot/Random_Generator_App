package org.wit.assignment.rand.models

data class ListModel(
    override var id: Int = 0,
    override var name: String = "",
    var items: List<Int> = emptyList()
) : Entity