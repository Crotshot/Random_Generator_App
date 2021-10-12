package org.wit.assignment.rand.models

interface ItemStore {
    fun findAll() : List<ItemModel>
    fun findOne(id : Int): ItemModel?
    fun create(item : ItemModel)
    fun update(item : ItemModel?)
    fun delete(item : ItemModel)
}