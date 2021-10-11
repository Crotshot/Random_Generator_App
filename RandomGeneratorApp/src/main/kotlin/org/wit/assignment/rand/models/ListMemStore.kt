import mu.KotlinLogging
import org.wit.assignment.rand.models.ListModel
import org.wit.assignment.rand.models.ListStore

private val logger = KotlinLogging.logger {}
var lastListId = 0

internal fun getListId(): Int {
    return lastListId++
}

class ListMemStore : ListStore{

    val lists = ArrayList<ListModel>()

    override fun findAll(): List<ListModel> {
        return lists
    }

    override fun findOne(id: Int) : ListModel? {
        var foundPlacemark: ListModel? = lists.find { i -> i.id == id }
        return foundPlacemark
    }

    override fun create(list : ListModel) {
        list.id = getListId()
        lists.add(list)
        logAll()
    }

    override fun update(list : ListModel) {
        var loclist = findOne(list.id!!)
        if (loclist != null) {
            loclist.name = list.name
            loclist.items = list.items
        }
    }

    override fun delete(list : ListModel) {
        var loclist = findOne(list.id!!)
        if (loclist != null) {
            throw NotImplementedError() //-------------------------------------------->> Implement
        }
    }

    internal fun logAll() {
        lists.forEach { logger.info("$it") }
    }
}