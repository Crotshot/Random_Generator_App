package org.wit.assignment.rand.models

data class ItemModel(
    override var id: Int,
    override var name: String,
    var weight: Float  = 0.0F,
) : Entity