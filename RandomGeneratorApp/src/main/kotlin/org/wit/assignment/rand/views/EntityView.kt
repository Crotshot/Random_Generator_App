import org.wit.assignment.rand.models.ItemModel
import org.wit.assignment.rand.models.ListModel

class EntityView {

    fun menu() : Int {

        var option : Int
        var input: String?

        println("MAIN MENU")
        println("1:     Create an Item")
        println("2:     Create a List")
        println("3:     Edit an Item")
        println("4:     Edit a List")
        println("5:     Search for Item")
        println("6:     Search for List")
        println("7:     Remove an item")
        println("8:     Remove a list")
        println("9:     Random selection")
        println("10:    List Items")
        println("11:    List Item Lists")
        println("-99:   Add dummy data(FOR TESTING)")
        println("-1:    Exiting App")
        println()
        print("Enter Option : ")
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
        print("Enter a name for the Item : ")
        item.name = readLine()!!
        print("Enter " + item.name + "'s weight : ")
        item.weight = weightInput()
        return item.name.isNotEmpty() && item.weight > 0F
    }

    fun updateItemData(item : ItemModel?) : Boolean {
        var tempName: String?
        var tempWeight: Float?

        if (item != null) {
            print("Enter a new name for [ " + item.name + " ] : ")
            tempName = readLine()!!
            print("Enter a new weight for [ " + item.weight + " ] : ")
            tempWeight = weightInput()!!//----------------------------------------------->ADD FLOAT VALIDATION

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


    fun addListData(list : ListModel) : Boolean {
        //Similar to addItemData except instead of weight we have a while loop for selecting an item to add to list
        throw NotImplementedError()
    /*
        println()
        print("Enter a Title : ")
        placemark.name = readLine()!!
        print("Enter a Description : ")
        placemark.description = readLine()!!

        return placemark.title.isNotEmpty() && placemark.description.isNotEmpty()
*/
    }

    fun updateListData(list : ListModel) : Boolean {
        throw NotImplementedError()
/*
        var tempName: String?
        var tempItems: List<Int>?

        if (list != null) {
            print("Enter a new Title for [ " + list.name + " ] : ")
            tempName = readLine()!!
            /*
            -----------------------------------------------------------------WHILE LOOP FOR ADDING ITEMS TO THE LIST

             */
            if (!tempName.isNullOrEmpty() && !tempItems.isNullOrEmpty()) {
                list.name = tempName
                list.items = tempItems
                return true
            }
        }
        return false
 */
    }

    fun getId() : Int {
        var strId : String?
        var searchId : Int
        println("Enter id to Search/Update : ")
        strId = readLine()!!
        searchId = if (strId.toLongOrNull() != null && !strId.isEmpty())  strId.toInt()
        else
            -999999
        return searchId
    }

    fun generateList(list: ListModel, duplicates: Boolean) {
        if (list == null) {
            println("Not a valid list. . .")
            return
        }
    }

    //Method created to stop the application displaying the menu straight after editing data
    fun enterToContinue(){
        println("Press Enter to continue. . . ")
        readLine()
    }

    fun filterItems(itemMemStore : ItemMemStore) : Boolean {
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
            var nameInput : String
            val filteredNames : ArrayList<String> = ArrayList()
            while(true){
                nameInput = readLine()!!
                if(nameInput.isNotEmpty()){
                    filteredNames.add(nameInput)
                    println("Would you like to add another names to the filter (y/n): ")
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
            var include = true;
            if(input.uppercase().contains("E")){
                println("Filtered to exclude $filteredNames")
                include = false;
            }
            else
                println("Filtered to include $filteredNames")

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
            temp.logAll()
        }
        return true;
    }
}