package org.wit.assignment.rand.controllers

import ItemMemStore
import RandomView
import mu.KotlinLogging
import org.wit.assignment.rand.models.ItemModel

class RandomController {

    val logger = KotlinLogging.logger {}

    init{
        logger.info { "Launching Random List Generator App" }
        println("Random List Generator Kotlin App Version 1.0")
    }

    fun start(){}
    fun menu(){}
    fun addItem(){}
    fun addList(){}
    fun editItem(){}
    fun editList(){}
    fun removeItem(){} //Check every list for item and delete itself
    fun removeList(){} //Delete a a list
    fun generate(){}//Choice of Int number and of bool duplicates

}