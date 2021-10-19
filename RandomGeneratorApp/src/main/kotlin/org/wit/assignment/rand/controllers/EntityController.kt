package org.wit.assignment.rand.controllers

import EntityView
import mu.KotlinLogging
import org.wit.assignment.rand.models.ItemJSONStore
import org.wit.assignment.rand.models.ItemModel
import org.wit.assignment.rand.models.ListJSONStore
import org.wit.assignment.rand.models.ListModel

class EntityController {

    val items = ItemJSONStore()
    val lists = ListJSONStore()
    val entityView = EntityView()
    val logger = KotlinLogging.logger {}

    init{
        logger.info { "Launching Random List Generator App" }
        println("Random List Generator Kotlin App Version 1.0")
    }

    fun start(){
        logger.info { "Launching Placemark Console App" }
        println("Placemark Kotlin App Version 1.0")
        entityView.enterToContinue()
        var input: Int

        do {
            input = menu()
            when(input) {
                1 -> addItem()
                2 -> addList()
                3 -> editItem()
                4 -> editList()
                5 -> searchItems()
                6 -> searchLists()
                7 -> removeItem()
                8 -> removeList()
                9 -> generate()
                10 -> showAllItems()
                11 -> showAllLists()
                12 -> deleteAllItems()
                13 -> deleteAllLists()
                -99 -> dummyData()
                -1 -> println("Exiting App")
                else -> println("Invalid Option")
            }
            println()
        } while (input != -1)
        logger.info { "Shutting Down Placemark Console App" }
    }

    fun menu():Int { return entityView.menu()}

    fun addItem(){
        val anItem = ItemModel()

        if(entityView.addItemData(anItem))
            items.create(anItem)
        else
            logger.info("Item not added")
        entityView.enterToContinue()
    }

    fun addList(){
        val aList = ListModel()

        if(entityView.addListData(aList, items))
            lists.create(aList)
        else
            logger.info("List not created")
        entityView.enterToContinue()
    }

    fun editItem(){
        entityView.listItems(items)
        var searchId = entityView.getId()
        val anItem = searchItems(searchId)

        if(entityView.updateItemData(anItem)){
            if(anItem == null) {
                println("Item not updated. . .")
                entityView.enterToContinue()
                return;
            }
            items.update(anItem)
            entityView.showItem(anItem)
        }
        else {
            println("Item not updated. . .")
        }
        entityView.enterToContinue()
    }

    fun editList(){
        entityView.listLists(lists)
        var searchId = entityView.getId()
        val aList = searchLists(searchId)

        if(entityView.updateListData(aList, items)){
            if(aList != null) {
                lists.update(aList)
                entityView.cleanListOfAll(aList, items)
            }
            entityView.showList(aList)
            entityView.listContains(aList, items)
        }
        else {
            println("List not updated. . .")
        }
        entityView.enterToContinue()
    }

    fun removeItem(){
        entityView.listItems(items)
        var searchId = entityView.getId()
        val anItem = searchItems(searchId)

        if(anItem != null){
            items.delete(anItem)
            entityView.cleanAllLists(lists, anItem)
            logger.info("Item removed : [ $anItem ]")
        }
        else{
            println("Item not removed. . .")
        }
        entityView.enterToContinue()
    }

    fun removeList(){
        entityView.listLists(lists)
        var searchId = entityView.getId()
        val aList = searchLists(searchId)

        if(aList != null){
            lists.delete(aList)
            logger.info("List removed : [ $aList ]")
        }
        else{
            println("List not removed. . .")
        }
        entityView.enterToContinue()
    } //Delete a a list

    fun generate(){
        entityView.cleanAllOfAll(lists, items)
        entityView.Randomgen(lists, items)
    }

    fun searchItems(){
        if(!entityView.filterItems(items,lists)) {
            val anItem = searchItems(entityView.getId())
            entityView.showItem(anItem)
        }
        entityView.enterToContinue()
    }

    fun searchItems(id: Int): ItemModel? {
        return items.findOne(id)
    }

    fun searchLists(){
        if(!entityView.filterLists(lists)) {
            val aList: ListModel = searchLists(entityView.getId()) ?: return
            entityView.showList(aList)
        }
        entityView.enterToContinue()
    }
    fun searchLists(id: Int): ListModel? {
        return lists.findOne(id)
    }

    fun showAllItems(){

        entityView.listItems(items)
        entityView.enterToContinue()
    }
    fun showAllLists(){
        entityView.cleanAllOfAll(lists, items)
        entityView.listLists(lists)
        entityView.enterToContinue()
    }

    private fun deleteAllItems() {
        if(entityView.areYouSure("delete all items?(y/n)")) {
            items.deleteAll(items)
            entityView.cleanAllOfAll(lists, items)
        }
        entityView.enterToContinue()
    }

    private fun deleteAllLists() {
        if(entityView.areYouSure("delete all lists?(y/n)"))
            lists.deleteAll(lists)
        entityView.enterToContinue()
    }

    fun dummyData(){
        ///*//Fair Dice -> Each side has an equal weight
        items.create(ItemModel(name = "One", weight = 1F))
        items.create(ItemModel(name = "Two", weight = 1F))
        items.create(ItemModel(name = "Three", weight = 1F))
        items.create(ItemModel(name = "Four", weight = 1F))
        //items.create(ItemModel(name = "Four", weight = 3F)) ->If used instead dice becomes loaded as Side Four is 3 times more likely to occur
        items.create(ItemModel(name = "Five", weight = 1F))
        items.create(ItemModel(name = "Six", weight = 1F))
        //*/

        // /* //Coin toss
        items.create(ItemModel(name = "Heads", weight = 10F))
        items.create(ItemModel(name = "Tails", weight = 10F))
        //*/
    }
}