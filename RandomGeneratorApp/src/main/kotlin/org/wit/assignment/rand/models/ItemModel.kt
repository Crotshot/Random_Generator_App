package org.wit.assignment.rand.models

data class ItemModel(
    override var id: Int = 0,
    override var name: String = "",
    var weight: Float  = 0.01F,
) : Entity