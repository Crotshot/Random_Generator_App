package org.wit.assignment.rand.models

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

import mu.KotlinLogging
import org.wit.assignment.rand.models.ItemModel
import org.wit.assignment.rand.models.ItemStore

import org.wit.assignment.rand.helpers.*
import java.util.*

private val logger = KotlinLogging.logger {}

val JSON_FILE_Items = "items.json"
val gsonBuilder_Items = GsonBuilder().setPrettyPrinting().create()
val listType_Items = object : TypeToken<java.util.ArrayList<ItemModel>>() {}.type

internal fun generateItemRandomId(): Int {
    return Random().nextInt()
}

class ItemJSONStore  : ItemStore{

    var items = ArrayList<ItemModel>()

    init {
        if (exists(JSON_FILE_Items)) {
            deserialize() //If a json exits set its items ArrayList to the deserialized data
        }
    }

    override fun findAll(): List<ItemModel> {
        return items
    }

    override fun findOne(id: Int): ItemModel? {
        return items.find { i -> i.id == id } //Find one item by ID
    }

    override fun create(item : ItemModel) { //Create an item adn add it to the array list
        item.id = generateItemRandomId()
        items.add(item)
        logAll()
        serialize() //Save all items
    }

    override fun update(item : ItemModel) { //Alter an item
        var locItem = findOne(item.id)  //Find an item by id
        if (locItem != null) {
            locItem.name = item.name
            locItem.weight = item.weight        //Edit its properties if it is not null
        }
        serialize() //Save
    }

    override fun delete(item : ItemModel) {
        var locItem = findOne(item.id!!)
        if (locItem != null) {
            items.remove(locItem)   //Find an item by id and delete it if it is null
        }
    }

    internal fun logAll() {
        items.forEach { logger.info("$it") }
    }
    //Log an item and return true if it logs successfully
    internal fun logOne(item : ItemModel?): Boolean{
        if(item != null)
            items.forEach {
                if(item == it) {
                    logger.info("$it")
                    return true
                }
            }
        return false;
    }
    //Save items to a json file
    private fun serialize() {
        val jsonString = gsonBuilder_Items.toJson(items, listType_Items)
        write(JSON_FILE_Items, jsonString)
    }
    //Load items from a json file
    private fun deserialize() {
        val jsonString = read(JSON_FILE_Items)
        items = Gson().fromJson(jsonString, listType_Items)
    }
    //Destroy all items and save
    override fun deleteAll(items: ItemJSONStore) {
        items.items.clear()
        serialize()
    }
}