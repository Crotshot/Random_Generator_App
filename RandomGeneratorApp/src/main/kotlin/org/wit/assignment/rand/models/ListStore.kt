package org.wit.assignment.rand.models

interface ListStore {
    fun findAll(): List<ListModel>
    fun findOne(id: Int): ListModel?
    fun create(list: ListModel)
    fun update(list: ListModel)
    fun delete(list: ListModel)
}