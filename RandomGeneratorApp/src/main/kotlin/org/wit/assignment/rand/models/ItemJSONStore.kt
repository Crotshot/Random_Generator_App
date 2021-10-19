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

val JSON_FILE = "items.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<ItemModel>>() {}.type

internal fun generateItemRandomId(): Int {
    return Random().nextInt()
}

class ItemJSONStore  : ItemStore{

    var items = ArrayList<ItemModel>()

    init {
        if (exists(JSON_FILE)) {
            deserialize()
        }
    }

    constructor(){}

    constructor(items: ArrayList<ItemModel>){
        for (item : ItemModel in items){
            this.items.add(item)
        }
    }

    override fun findAll(): List<ItemModel> {
        return items
    }

    override fun findOne(id: Int): ItemModel? {
        return items.find { i -> i.id == id }
    }

    override fun create(item : ItemModel) {
        item.id = generateItemRandomId()
        items.add(item)
        logAll()
        serialize()
    }

    override fun update(item : ItemModel) {
        var locItem = findOne(item.id)
        if (locItem != null) {
            locItem.name = item.name
            locItem.weight = item.weight
        }
        serialize()
    }

    override fun delete(item : ItemModel) {
        var locItem = findOne(item.id!!)
        if (locItem != null) {
            items.remove(locItem)
        }
    }

    internal fun logAll() {
        items.forEach { logger.info("$it") }
    }

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

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(items, listType)
        write(JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(JSON_FILE)
        items = Gson().fromJson(jsonString, listType)
    }
}