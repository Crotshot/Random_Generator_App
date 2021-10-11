package org.wit.assignment.rand.controllers

import ItemMemStore
import ListMemStore
import EntityView
import mu.KotlinLogging

class RandomController {

    val items = ItemMemStore()
    val lists = ListMemStore()
    val entityView = EntityView()
    val logger = KotlinLogging.logger {}

    init{
        logger.info { "Launching Random List Generator App" }
        println("Random List Generator Kotlin App Version 1.0")
    }

    fun start(){

        logger.info { "Launching Placemark Console App" }
        println("Placemark Kotlin App Version 1.0")

        var input: Int

        do {
            input = menu()
            when(input) {
                1 -> addItem()
                2 -> addList()
                3 -> editItem()
                4 -> editList()
                3 -> entityView.listItems(items)
                4 -> entityView.listLists(lists)
                5 -> searchItems()
                6 -> searchLists()
                7 -> removeItem()
                8 -> removeList()
                9 -> generate()
                -99 -> dummyData()
                -1 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != -1)
        logger.info { "Shutting Down Placemark Console App" }
    }

    fun menu():Int { return entityView.menu()}
    fun addItem(){}
    fun addList(){}
    fun editItem(){}
    fun editList(){}
    fun removeItem(){} //Check every list for item and delete itself
    fun removeList(){} //Delete a a list
    fun generate(){}//Choice of Int number and of bool duplicates
    fun searchItems(){}
    fun searchLists(){}
    fun dummyData(){}
}