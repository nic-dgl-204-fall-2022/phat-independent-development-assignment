package com.example.daos

import at.favre.lib.crypto.bcrypt.BCrypt
import com.benasher44.uuid.uuid4
import com.example.item.AffectAttributes
import com.example.item.ItemCollection
import com.example.models.UseItemModel
import com.example.models.ItemModel
import com.example.util.Database
import com.example.util.QueryResult
import com.mongodb.client.result.UpdateResult
import kotlinx.serialization.Serializable
import org.litote.kmongo.*

@Serializable
data class UserDAO(
    val id: String,
    val username: String,
    val hashedPassword: String,
    val level: Int? = null,
    val expPoints: Int? = null,
    val maxExpPoints: Int? = null,
    val jwtToken: String? = null,
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val coins: Int? = null,
    val pokemon: List<String>? = null,
    var items: List<ItemModel>? = null
) {
}

class UserCollection() {
    private val instance = Database().instance.getCollection<UserDAO>("users") //KMongo extension method

    fun insertOne(username: String, password: String): QueryResult {
        val id = uuid4().toString()
        val hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray())

        // Check to see if the username already existed.
        val existedUser = instance.findOne(UserDAO::username eq username.lowercase())
        if (existedUser != null) {
            return QueryResult(false, "Username is already taken.")
        }

        val insertResult = QueryResult(false)
        try {
            val user = UserDAO(
                id,
                username.lowercase(),
                hashedPassword,
                level = 1,
                expPoints = 0,
                maxExpPoints = 1000,
                coins = 0,
                items = listOf(ItemModel("b6eaa392-05eb-4a5f-9d91-83f55fb731e2", 1000),
                    ItemModel("112c9eac-895f-4346-9109-cfcd8a640738", 100)),
                pokemon = listOf(
                    "89cd54fa-6744-4cb0-b42b-50ceadb5a28e",
                    "b6800af1-6076-4cf9-a74d-266d78eb7fec",
                    "52830674-de5f-4951-8f09-8803454d4b5c"
                )
            )
            instance.insertOne(user)
            insertResult.done = true
            insertResult.data = id
        } catch (e: Exception) {
            insertResult.error = e.message
        }

        return insertResult
    }

    fun getUserById(id: String): UserDAO? {
        return instance.findOne(UserDAO::id eq id)
    }

    fun getUserByUsername(username: String): UserDAO? {
        return instance.findOne(UserDAO::username eq username)
    }

    fun updateJwtToken(id: String, newJwtToken: String?): UpdateResult {
        return instance.updateOne(UserDAO::id eq id, setValue(UserDAO::jwtToken, newJwtToken))
    }

    fun useItem(userId: String, items: List<UseItemModel>) {
        val user = instance.findOne(UserDAO::id eq userId)

        if (user?.items != null) {
            user.items!!.forEach { userItem ->
                items.forEach {
                    if (it.id == userItem.id && userItem.amount > 0 && userItem.amount > it.amount) {
                        userItem.amount -= it.amount

                        // Affect to Pokemon
                        val item = ItemCollection().getItemById(it.id)
                        if (item?.affect != null) {
                            when {
                                // Add EXP
                                item.affect.contains(AffectAttributes.EXP) -> PokemonCollection().addExp(
                                    it.pokemonId,
                                    item.affect.getValue(AffectAttributes.EXP)
                                )
                                // Add Power
                                item.affect.contains(AffectAttributes.POWER) -> PokemonCollection().addPower(
                                    it.pokemonId,
                                    item.affect.getValue(AffectAttributes.POWER)
                                )
                            }
                        }
                    }
                }
            }

            instance.updateOne(UserDAO::id eq user.id, setValue(UserDAO::items, user.items))
        }
    }
}