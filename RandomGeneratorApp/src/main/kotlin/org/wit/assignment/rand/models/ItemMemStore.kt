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
        item.id = getItemId()
        items.add(item)
        logAll()
    }

    override fun update(item : ItemModel) {
        var locItem = findOne(item.id)
        if (locItem != null) {
            locItem.name = item.name
            locItem.weight = item.weight
        }
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
}