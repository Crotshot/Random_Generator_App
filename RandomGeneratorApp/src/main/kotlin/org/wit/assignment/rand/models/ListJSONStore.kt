package org.wit.assignment.rand.models

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

import mu.KotlinLogging
import org.wit.assignment.rand.models.ListModel
import org.wit.assignment.rand.models.ListStore

import org.wit.assignment.rand.helpers.*
import java.util.*

private val logger = KotlinLogging.logger {}

val JSON_FILE_Lists = "lists.json"
val gsonBuilder_Lists = GsonBuilder().setPrettyPrinting().create()
val listType_Lists = object : TypeToken<java.util.ArrayList<ListModel>>() {}.type


internal fun generateListRandomId(): Int {
    return Random().nextInt()
}

class ListJSONStore : ListStore{

    var lists = ArrayList<ListModel>()

    init {
        if (exists(JSON_FILE_Lists)) {
            deserialize() //Load data from json file
        }
    }

    override fun findAll(): List<ListModel> {
        return lists
    }

    override fun findOne(id: Int): ListModel? {
        return lists.find { i -> i.id == id }
    }

    override fun create(list : ListModel) {
        list.id = generateListRandomId()
        lists.add(list)//Add a list to the Item List Array List
        logAll()
        serialize()
    }

    override fun update(list : ListModel) {
        var loclist = findOne(list.id)
        if (loclist != null) {
            loclist.name = list.name
            loclist.items.clear()
            for(item : Int in list.items){
                loclist.items.add(item)
            }
        }
        serialize()
    }

    override fun delete(list : ListModel) {
        var loclist = findOne(list.id)
        if (loclist != null) {//If the list is not null, delete it
            lists.remove(loclist)
        }
    }

    internal fun logAll() {
        lists.forEach { logger.info("$it") }
    }
    //Log and list and return true if successfully executed
    internal fun logOne(list : ListModel?): Boolean{
        if(list != null)
            lists.forEach {
                if(list == it) {
                    logger.info("$it")
                    return true
                }
            }
        return false;
    }
    //Save to json file
    private fun serialize() {
        val jsonString = gsonBuilder_Lists.toJson(lists, listType_Lists)
        write(JSON_FILE_Lists, jsonString)
    }
    //Load from json file
    private fun deserialize() {
        val jsonString = read(JSON_FILE_Lists)
        lists = Gson().fromJson(jsonString, listType_Lists)
    }
    //Delete all lists and save
    override fun deleteAll(lists: ListJSONStore) {
        lists.lists.clear()
        serialize()
    }
}