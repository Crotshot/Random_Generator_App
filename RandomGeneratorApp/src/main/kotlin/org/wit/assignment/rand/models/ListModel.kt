package org.wit.assignment.rand.models

data class ListModel(
    override var id: Int,
    override var name: String,
    var items: List<Int> = emptyList()
) : Entity