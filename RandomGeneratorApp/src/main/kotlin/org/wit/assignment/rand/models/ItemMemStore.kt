import mu.KotlinLogging
import org.wit.assignment.rand.models.ItemModel
import org.wit.assignment.rand.models.ItemStore

private val logger = KotlinLogging.logger {}
var lastItemId = 0

internal fun getItemId(): Int {
    return lastItemId++
}

class ItemMemStore : ItemStore{

    val items = ArrayList<ItemModel>()

    override fun findAll(): List<ItemModel> {
        return items
    }

    override fun findOne(id: Int) : ItemModel? {
        var foundPlacemark: ItemModel? = items.find { i -> i.id == id }
        return foundPlacemark
    }

    override fun create(item : ItemModel) {
        item.id = getItemId()
        items.add(item)
        logAll()
    }

    override fun update(item : ItemModel) {
        var locItem = findOne(item.id!!)
        if (locItem != null) {
            locItem.name = item.name
            locItem.weight = item.weight
        }
    }

    override fun delete(item : ItemModel) {
        var locItem = findOne(item.id!!)
        if (locItem != null) {
            throw NotImplementedError() //-------------------------------------------->> Implement
        }
    }

    internal fun logAll() {
        items.forEach { logger.info("$it") }
    }
}