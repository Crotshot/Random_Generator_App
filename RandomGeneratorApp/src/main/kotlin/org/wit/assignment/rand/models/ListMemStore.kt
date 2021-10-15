import mu.KotlinLogging
import org.wit.assignment.rand.models.ItemModel
import org.wit.assignment.rand.models.ListModel
import org.wit.assignment.rand.models.ListStore

private val logger = KotlinLogging.logger {}
var lastListId = 0

internal fun getListId(): Int {
    return lastListId++
}

class ListMemStore : ListStore{

    val lists = ArrayList<ListModel>()

    constructor(){}

    constructor(lists: ArrayList<ListModel>){
        for (list : ListModel in lists){
            this.lists.add(list)
        }
    }

    override fun findAll(): List<ListModel> {
        return lists
    }

    override fun findOne(id: Int): ListModel? {
        return lists.find { i -> i.id == id }
    }

    override fun create(list : ListModel) {
        list.id = getListId()
        lists.add(list)
        logAll()
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
    }

    override fun delete(list : ListModel) {
        var loclist = findOne(list.id)
        if (loclist != null) {
            lists.remove(loclist)
        }
    }

    internal fun logAll() {
        lists.forEach { logger.info("$it") }
    }

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
}