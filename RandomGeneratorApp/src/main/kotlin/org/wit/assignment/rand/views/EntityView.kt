import org.wit.assignment.rand.models.ItemModel
import org.wit.assignment.rand.models.ListModel
import kotlin.random.Random

class EntityView {

    fun menu() : Int {

        var option : Int
        var input: String?

        println("MAIN MENU")
        println("1:     Create an Item")
        println("2:     Create a List")
        println("3:     Edit an Item")
        println("4:     Edit a List")
        println("5:     Search/Filter for Item(s)")
        println("6:     Search/Filter for List(s)")
        println("7:     Remove an item")
        println("8:     Remove a list")
        println("9:     Random selection")
        println("10:    List Items")
        println("11:    List Item Lists")
        println("-99:   Add dummy data(FOR TESTING)")
        println("-1:    Exiting App")
        println()
        println("Enter Option : ")
        input = readLine()!!
        option = if (input.toIntOrNull() != null && !input.isEmpty()) input.toInt() else -9
        return option
    }

    fun listItems(items : ItemMemStore) {
        println("List Items")
        println()
        items.logAll()
        println()
    }

    fun listLists(lists : ListMemStore) {
        println("List Item Lists")
        println()
        lists.logAll()
        println()
    }

    fun showItem(item : ItemModel?) {
        if(item != null)
            println("Item Details [ $item ]")
        else
            println("Item Not Found...")
    }

    fun showList(list : ListModel?) {
        if(list != null)
            println("Item Details [ $list ]")
        else
            println("Item Not Found...")
    }

    fun addItemData(item : ItemModel) : Boolean {
        //Create an item with a name and weight, auto assign id using a while loop, starting at zero and incrementing up
        println()
        println("Enter a name for the Item : ")
        item.name = readLine()!!
        println("Enter " + item.name + "'s weight : ")
        item.weight = weightInput()
        return item.name.isNotEmpty() && item.weight > 0F
    }

    fun updateItemData(item : ItemModel?) : Boolean {
        var tempName: String?
        var tempWeight: Float?

        if (item != null) {
            println("Enter a new name for [ " + item.name + " ] : ")
            tempName = readLine()!!
            println("Enter a new weight for [ " + item.weight + " ] : ")
            tempWeight = weightInput()

            if (!tempName.isNullOrEmpty() && tempWeight > 0F) {
                item.name = tempName
                item.weight = tempWeight
                return true
            }
        }
        return false
    }

    private fun weightInput(): Float {
        var input = readLine()!!
        return if (input.toFloatOrNull() != null && !input.isEmpty())
            if (input.toFloat() > 0F)
                input.toFloat()
            else
                if(input.toFloat() == 0F)
                    0.01F
                else
                    input.toFloat() * -1
        else
            -1F
    }


    fun addListData(list : ListModel, items : ItemMemStore) : Boolean {
        if (items.items.size < 2){
            println("Too few Items to construct an item list")
            return false
        }
        println()
        println("Enter a name for the Item List : ")
        while(list.name.isEmpty()){
            list.name = readLine()!!
        }

        println("Enter Items ID's for Item list")
        itemSelection(list, items)

        return list.name.isNotEmpty() && list.items.size >= 2
    }

    fun updateListData(list : ListModel?, items : ItemMemStore) : Boolean {
        //Ask user to add at least 2 items to list
        if (items.items.size < 2){
            println("Too few Items available to edit the list")
            return false
        }
        if (list != null) {
            list.items.clear()
            println("Enter a new name for [ " + list.name + " ] : ")
            val tempName: String? = readLine()!!

            println("Enter new Items ID's for Item list")
            itemSelection(list, items)

            if (!tempName.isNullOrEmpty()) {
                list.name = tempName
                return true
            }
        }
        return false
    }

    private fun itemSelection(list : ListModel, items : ItemMemStore){
        items.logAll()
        enterToContinue()
        var itemID : Int
        while(true) {
            println("Add the first item to the list by ID")
            itemID = itemInput()
            if (items.findOne(itemID) != null) {
                list.items.add(itemID)
                break
            }
            else{
                println("Please add a valid item")
            }
        }
        while(true) {
            println("Add the second item to the list by ID")
            itemID = itemInput()
            if (items.findOne(itemID) != null) {
                list.items.add(itemID)
                break
            }
            else{
                println("Please add a valid item")
            }
        }
        var input : String
        while(true) {
            println("Would you like to put additional items in the list? (y/n)")
            input = readLine()!!
            if (!input.uppercase().contains("Y"))
                return
            println("Add another item to the list by ID")
            itemID = itemInput()
            if (items.findOne(itemID) != null) {
                list.items.add(itemID)
            }
            else{
                if(itemID == -2)
                    break
                println("Please add a valid item")
            }
        }
    }

    private fun itemInput(): Int {
        var input = readLine()!!
        return if (input.toIntOrNull() != null && !input.isEmpty())
            if (input.toInt() >= 0)
                input.toInt()
            else
                -1
        else
            -1
    }

    fun listContains(list : ListModel?, items: ItemMemStore){
        if(list != null)
            for(item : Int in list.items){
                if(!items.logOne(items.findOne(item))){
                    cleanListOfAll(list, items)
                }
            }
    }

    fun cleanAllLists(lists : ListMemStore, item : ItemModel){        //-> Removes a single item from all lists
        //Called when items are deleted
        println("Cleaning all lists, of removed item")
        for(list in lists.lists){
            for(id in list.items){
                if(item.id == id){
                    list.items.remove(id)
                }
            }
        }
    }

    fun cleanListOfAll(list : ListModel, items : ItemMemStore){          //-> Removes all deleted items from a list
        //Clear a list of all items that have been deleted
        for(item : Int in list.items){
            //findOne and if null remove item from list
            if(items.findOne(item) == null){
                list.items.remove(item)
            }
        }
    }

    fun cleanAllOfAll(lists : ListMemStore, items : ItemMemStore){
        for(list in lists.lists){
            cleanListOfAll(list, items)
        }
    }

    fun getId() : Int {
        var strId : String?
        var searchId : Int
        println("Enter id to Search : ")
        strId = readLine()!!
        searchId = if (strId.toLongOrNull() != null && !strId.isEmpty())  strId.toInt()
        else
            -999999
        return searchId
    }

    //Method created to stop the application displaying the menu straight after editing data
    fun enterToContinue(){
        println("Press Enter to continue. . . ")
        readLine()
    }

    fun filterItems(itemMemStore : ItemMemStore, listMemStore: ListMemStore) : Boolean {
        println("Would you like to filter search? (y/n) : ")
        var input : String
        input = readLine()!!
        if (!input.uppercase().contains("Y"))
            return false
        println("Would you like to filter names or weights? (n/w) : ")
        input = readLine()!!
        if (!input.uppercase().contains("N") && !input.uppercase().contains("W"))
            return false

        if(input.uppercase().contains("N")){
            println("Enter name to filter (Case Sensitive): ")
            val filteredNames : ArrayList<String> = ArrayList()
            var include = nameFilter(filteredNames);
            val itemsToDisplay = ArrayList<ItemModel>()

            for (item : ItemModel in itemMemStore.items){
                var found = false
                for(i in 0 until filteredNames.size){
                    if (item.name.contains(filteredNames[i])) {
                        found = true
                        break
                    }
                }
                if (found == include) {
                    itemsToDisplay.add(item)
                }
            }
            var temp = ItemMemStore(itemsToDisplay)
            temp.logAll()
            filteredItemsToList(temp,listMemStore)
        }
        else{
            println("Enter weight to filter (Example 10.025): ")
            var weightInput : String
            var weightCheck : Float
            while(true) {
                weightInput = readLine()!!
                if (weightInput.toFloatOrNull() != null && !weightInput.isEmpty()) {
                    weightCheck = weightInput.toFloat()
                    break
                } else
                    println("Invalid value entered, please enter a correct value EG 10.024 or 123")
            }

            println("Would you like the filter to be greater than or less than? (g/l): ")
            input = readLine()!!
            var greaterThan = true;
            if(input.uppercase().contains("L")){
                greaterThan = false
            }

            val itemsToDisplay = ArrayList<ItemModel>()
            for (item : ItemModel in itemMemStore.items){
                if(item.weight > weightCheck == greaterThan){
                    itemsToDisplay.add(item)
                }
            }

            var temp = ItemMemStore(itemsToDisplay)

            if (temp.items.size == 0)
                return true

            temp.logAll()
            filteredItemsToList(temp,listMemStore)
        }
        return true
    }

    fun filteredItemsToList(itemsToMakeListFrom : ItemMemStore, listMemStore: ListMemStore){
        if(itemsToMakeListFrom.items.size == 0)
            return
        println("Would you like to make a list from filtered search? (y/n) : ")
        var input : String = readLine()!!
        if (!input.uppercase().contains("Y"))
            return
        val tempList = ListModel()
        println()
        println("Enter a name for the new List : ")
        tempList.name = readLine()!!
        for (item in itemsToMakeListFrom.items){
            tempList.items.add(item.id)
        }
        listMemStore.create(tempList)
    }

    fun filterLists(listMemStore: ListMemStore) : Boolean{

        println("Would you like to filter search? (y/n) : ")
        var input : String
        input = readLine()!!
        if (!input.uppercase().contains("Y"))
            return false
        println("Would you like to filter by names or item id's or by number of items? (n/d/i) : ")
        input = readLine()!!
        if (!input.uppercase().contains("N") && !input.uppercase().contains("D") && !input.uppercase().contains("I"))
            return false

        //Filtering by name
        if(input.uppercase().contains("N")){
            println("Enter name to filter (Case Sensitive): ")
            val filteredNames : ArrayList<String> = ArrayList()
            var include = nameFilter(filteredNames)

            val listsToDisplay = ArrayList<ListModel>()

            for (list : ListModel in listMemStore.lists){
                var found = false
                for(i in 0 until filteredNames.size){
                    if (list.name.contains(filteredNames[i])) {
                        found = true
                        break
                    }
                }
                if (found == include) {
                    listsToDisplay.add(list)
                }
            }
            mergeFilteredLists(ListMemStore(listsToDisplay),listMemStore)
        }
        //Filtering by an item ID
        else if(input.uppercase().contains("D")) {
            val filteredIDs: ArrayList<Int> = ArrayList()
            while(true){
                println("Enter id to filter (eg 0, 1, 95, 43): ")
                input = readLine()!!
                if (input.toIntOrNull() != null) {
                    filteredIDs.add(input.toInt())
                }
                else {
                    if (filteredIDs.size == 0) {
                        println("No value entered, please filter for at one ID")
                    }
                    else{
                        println("No value entered, exiting")
                        break
                    }
                }
            }

            println("Would you like the filter to include or exclude these ID's (i/e): ")
            input = readLine()!!
            var include = input.uppercase().contains("I")

            val listsToDisplay = ArrayList<ListModel>()
            var contains = false
            for(list : ListModel in listMemStore.lists) {
                contains = true
                for(ID in filteredIDs) {
                    if(!list.items.contains(ID)){
                        contains = false
                        break
                    }
                }
                if(contains == include)
                    listsToDisplay.add(list)
            }

            if(listsToDisplay.size == 0){
                println("Everything filtered out")
                enterToContinue()
                return true
            }

            mergeFilteredLists(ListMemStore(listsToDisplay),listMemStore)
        }
        else{//Filtering by Number of Items
            println("How many items to filter for:")
            input = readLine()!!
            var itemAmount : Int
            if (input.toIntOrNull() != null) {
                itemAmount = input.toInt()
            }
            else{
                return true
            }

            println("Would you like the filter to look for ids >= or <=? (g/l): ")
            input = readLine()!!
            var greaterThan = if(input.uppercase().contains("G")){
                println("Filtered to look for >= $itemAmount")
                true
            } else {
                println("Filtered to look for <= $itemAmount")
                false
            }

            val listsToDisplay = ArrayList<ListModel>()
            for(list : ListModel in listMemStore.lists) {
                if((list.items.size >= itemInput()) == greaterThan){
                    listsToDisplay.add(list)
                }
            }

            mergeFilteredLists(ListMemStore(listsToDisplay),listMemStore)
        }
        return true
    }

    fun mergeFilteredLists(temp : ListMemStore, lists : ListMemStore){
        temp.logAll()
        if(temp.lists.size == 0)
            return
        println("Would you like to make a new list from filtered search? (y/n) : ")
        var input : String = readLine()!!
        if (!input.uppercase().contains("Y"))
            return
        val tempList = ListModel()
        println()
        println("Enter a name for the new List : ")
        tempList.name = readLine()!!
        for (list : ListModel in temp.lists ){
            for (itemIDs in list.items){
                tempList.items.add(itemIDs)
            }
        }
        lists.create(tempList)
    }

    fun nameFilter(names : ArrayList<String>): Boolean{
        var input : String
        while(true){
            input = readLine()!!
            if(input.isNotEmpty()){
                names.add(input)
                println("Would you like to add another name to the filter (y/n): ")
                input = readLine()!!
                if (input.uppercase().contains("N"))
                    break
                println("Enter additional name to filter (Case Sensitive):")
            }
            else
                println("Nothing entered, please enter a non empty value")
        }

        println("Would you like the filter to include or exclude filtered names? (i/e): ")
        input = readLine()!!
        return if(input.uppercase().contains("E")){
            println("Filtered to exclude $names")
            false
        } else {
            println("Filtered to include $names")
            true
        }
    }

    fun Randomgen(lists: ListMemStore, items: ItemMemStore) {
        cleanAllOfAll(lists,items)
        var input : String
        var num = 0
        var duplicates = false
        lists.logAll()
        println("Enter ID of the list you want to use:")
        input = readLine()!!
        if (input.toIntOrNull() != null) {
            num = input.toInt()
        }
        else{
            println("Please enter a natural number for list ID")
            enterToContinue()
        }
        var temp : ListModel? = lists.findOne(num)
        var list : ListModel = if(temp != null) {temp}
        else{println("Invalid list ID")
        return}

        lists.logOne(list)

        println("How many items do you want to display:")
        input = readLine()!!
        if (input.toIntOrNull() != null) {
            num = input.toInt()
        }
        else{
            println("Items to display should be a natural number")
            enterToContinue()
        }

        println("Do you want duplicates(y/n):")
        input = readLine()!!
        if (input.uppercase().contains("Y")) {
            duplicates = true
        }

        //Generate
        generate(list,duplicates,num, items)
        //Loop for regenerating
        while(true){
            println("Would you like to generate another?(y/n)")
            if (input.uppercase().contains("Y")) {
                generate(list,duplicates,num, items)
            }
            else{
                break
            }
        }
    }

    fun generate(list: ListModel, duplicates: Boolean, numberOfGens : Int, items: ItemMemStore) {
        println("Generating " + numberOfGens + " items from " + list.name)
        var itemGenList : ArrayList<ItemModel> = ArrayList()
        for (item in list.items ){
            itemGenList.add(items.findOne(item)!!)
        }

        var totalWeight : Float = 0.0F
        for (item in itemGenList) { totalWeight += item.weight }
        var randomNum = 0.0F
        var count = 0.0F

        var i = 0
        var loopBreak = 0
        var generatedItems : ArrayList<ItemModel> = ArrayList()
        while(i < numberOfGens){
            randomNum = Random.nextFloat() * totalWeight
            count = 0.0F
            var found = false
            for(item in itemGenList){
                if(generatedItems.contains(item) && !duplicates) {
                    break
                }
                else if(randomNum >= count && randomNum < count + item.weight){
                  println("-> " + item.name)
                  generatedItems.add(item)
                  found = true
                  break
                }

                count += item.weight

            }
            if(found)
                i++
            else
                loopBreak++

            if(loopBreak > 10000){
                break
            }
        }

        enterToContinue()
    }
}















