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
        println("3:     List all Items")
        println("4:     List all Lists")
        println("5:     Search for Item")
        println("6:     Search for List")
        println("7:     Remove an item")
        println("8:     Remove a list")
        println("9:     Random selection")
        println("-99:   Add dummy data(FOR TESTING)")
        println("-1:    Exiting App")
        println()
        print("Enter Option : ")
        input = readLine()!!
        option = if (input.toIntOrNull() != null && !input.isEmpty())
            input.toInt()
        else
            -9
        return option
    }

    fun listItems(items : ItemMemStore) {
        println("List All Items")
        println()
        items.logAll()
        println()
    }

    fun listLists(lists : ListMemStore) {
        println("List All Lists")
        println()
        lists.logAll()
        println()
    }

    fun showItem(item : ItemModel) {
        if(item != null)
            println("Item Details [ $item ]")
        else
            println("Item Not Found...")
    }

    fun showList(list : ListModel) {
        if(list != null)
            println("Item Details [ $list ]")
        else
            println("Item Not Found...")
    }

    fun addItemData(item : ItemModel) : Boolean {
        //Create an item with a name and weight, auto assign id using a while loop, starting at zero and incrementing up
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

    fun updateItemData(item : ItemModel) : Boolean {
        var tempName: String?
        var tempWeight: Float?

        if (item != null) {
            print("Enter a new Title for [ " + item.name + " ] : ")
            tempName = readLine()!!
            print("Enter a new Description for [ " + item.weight + " ] : ")
            tempWeight = readLine()!!//----------------------------------------------->ADD FLOAT VALIDATION

            if (!tempName.isNullOrEmpty() && !tempWeight.isNullOrEmpty()) {
                item.name = tempName
                item.weight = tempWeight
                return true
            }
        }
        return false
    }

    fun updateListData(list : ListModel) : Boolean {
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
    }

    fun getId() : Int {
        var strId : String? // String to hold user input
        var searchId : Int // Long to hold converted id
        print("Enter id to Search/Update : ")
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
}
}