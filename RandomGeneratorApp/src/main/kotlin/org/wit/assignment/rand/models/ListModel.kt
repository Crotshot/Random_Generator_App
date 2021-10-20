package org.wit.assignment.rand.models

data class ListModel(
    override var id: Int = 0,
    override var name: String = "",
    val items: ArrayList<Int> = ArrayList() //An array list of items
) : Entity