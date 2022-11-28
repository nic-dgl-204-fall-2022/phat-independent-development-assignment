package com.example.item

import com.benasher44.uuid.uuid4
import com.example.daos.PokemonDAO
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
) {

}

class ItemCollection() {
    private val instance = Database().instance.getCollection<ItemDAO>("items")

    fun initialize() {
        instance.drop()
        instance.insertMany(
            listOf(
                ItemDAO(
                    "0c7a63c6-7598-49ad-8c2c-1f86f81f2ba5",
                    "Standard Pokeball",
                    ItemTypes.Pokeball,
                    "Throw it to wild pokemon and it will capture it for you.",
                    hashMapOf(AffectAttributes.CAPTURE to 50)
                ),
                ItemDAO(
                    "e99ac306-f8ae-43b4-a0fa-28c263a20709",
                    "Electric Pokeball",
                    ItemTypes.Pokeball,
                    "Throw it to wild pokemon and it will capture it for you.",
                    hashMapOf(AffectAttributes.CAPTURE to 100)
                ),
                ItemDAO(
                    "b6eaa392-05eb-4a5f-9d91-83f55fb731e2",
                    "Apple",
                    ItemTypes.ConsumableItems,
                    "Let your pokemon take a bite and it will increase its EXP points.",
                    hashMapOf(AffectAttributes.EXP to 100)
                ),
                ItemDAO(
                    "0f9c405d-82bc-485a-a27b-78b459c7d614",
                    "Burger",
                    ItemTypes.ConsumableItems,
                    "Let your pokemon take a bite and it will increase its EXP points.",
                    hashMapOf(AffectAttributes.EXP to 200)
                ),
                ItemDAO(
                    "f23b65c8-384e-407e-964c-44d76ed82d22",
                    "Electric Medal",
                    ItemTypes.MysticItems,
                    "It increases your pokemon power",
                    hashMapOf(AffectAttributes.POWER to 1000)
                ),
                ItemDAO(
                    "6fd32d27-720e-4a64-b18e-1024c1953951",
                    "Fire Medal",
                    ItemTypes.MysticItems,
                    "It increases your pokemon power",
                    hashMapOf(AffectAttributes.POWER to 1000)
                ),
                ItemDAO(
                    "112c9eac-895f-4346-9109-cfcd8a640738",
                    "Grass Medal",
                    ItemTypes.MysticItems,
                    "It increases your pokemon power",
                    hashMapOf(AffectAttributes.POWER to 1000)
                ),
            )
        )
    }

    fun getItems(): List<ItemDAO> {
        return instance.find().toList()
    }

    fun getItemById(id: String): ItemDAO? {
        return instance.findOne(ItemDAO::id eq id)
    }
}