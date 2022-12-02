package com.example.item

import com.benasher44.uuid.uuid4
import com.example.daos.*
import com.example.util.Database
import com.example.util.QueryResult
import com.example.util.capitalize
import kotlinx.serialization.Serializable
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection

enum class ItemTypes {
    Pokeball, MysticItems, ConsumableItems
}

enum class AffectAttributes {
    EXP, POWER, CAPTURE
}

@Serializable
data class ItemDAO(
    val id: String,
    val name: String,
    val type: ItemTypes,
    val description: String? = null,
    val affect: HashMap<AffectAttributes, Int>,
    val imgName: String? = null
) {}

val rewardItems = listOf(
    StandardPokeball,
    ElectricPokeBall,
    Apple,
    Burger,
    ElectricMedal,
    GrassMedal,
    FireMedal
)

class ItemCollection() {
    private val instance = Database().instance.getCollection<ItemDAO>("items")

    fun initialize() {
        instance.drop()
        instance.insertMany(rewardItems)
    }

    fun getItems(): List<ItemDAO> {
        return instance.find().toList()
    }

    fun getItemById(id: String): ItemDAO? {
        return instance.findOne(ItemDAO::id eq id)
    }
}