package com.example.daos

import at.favre.lib.crypto.bcrypt.BCrypt
import com.benasher44.uuid.uuid4
import com.example.item.AffectAttributes
import com.example.item.ItemCollection
import com.example.item.ItemDAO
import com.example.item.rewardItems
import com.example.models.BattleResultModel
import com.example.models.UseItemModel
import com.example.models.ItemModel
import com.example.util.Database
import com.example.util.QueryResult
import com.mongodb.client.result.UpdateResult
import kotlinx.serialization.Serializable
import org.litote.kmongo.*
import kotlin.math.exp

enum class Activity {
    CATCH, BATTLE
}

@Serializable
data class UserDAO(
    val id: String,
    val username: String,
    val hashedPassword: String,
    var level: Int,
    var expPoints: Int,
    var maxExpPoints: Int,
    val jwtToken: String? = null,
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val coins: Int? = null,
    var pokemon: List<String>? = null,
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
                items = listOf(
                    ItemModel("b6eaa392-05eb-4a5f-9d91-83f55fb731e2", 1000),
                    ItemModel("112c9eac-895f-4346-9109-cfcd8a640738", 100),
                    ItemModel("0c7a63c6-7598-49ad-8c2c-1f86f81f2ba5", 50),
                    ItemModel("e99ac306-f8ae-43b4-a0fa-28c263a20709", 5),
                ),
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

    private fun addExp(userId: String, expPoints: Int) {
        val user = instance.findOne(UserDAO::id eq userId)

        if (user?.expPoints != null && expPoints > 0) {
            val exp = user.expPoints + expPoints
            if(exp >= user.maxExpPoints) {
                user.level = user.level.plus(1)
                user.expPoints = exp - user.maxExpPoints
                user.maxExpPoints += (user.maxExpPoints * 0.4).toInt()
            } else {
                user.expPoints = exp
            }

            instance.replaceOne(UserDAO::id eq user.id, user)
        }
    }

    private fun getActivityRewards(userId: String, activity: Activity) {
        when (activity) {
            Activity.CATCH -> this.addExp(userId, 2_000)
            Activity.BATTLE -> this.addExp(userId, 1_000)
        }
    }

    fun useItem(userId: String, items: List<UseItemModel>) {
        val user = instance.findOne(UserDAO::id eq userId)

        if (user?.items != null) {
            user.items!!.forEach { userItem ->
                items.forEach {
                    if (it.id == userItem.id && userItem.amount > 0 && userItem.amount > it.amount) {
                        // Affect to Pokemon
                        val item = ItemCollection().getItemById(it.id)
                        if (item != null) {
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

                            // Decrease amount
                            userItem.amount -= it.amount
                        }

                    }
                }
            }

            instance.updateOne(UserDAO::id eq user.id, setValue(UserDAO::items, user.items))
        }
    }

    fun foundWildPokemon(userId: String, pokemonId: String) {
        val user = instance.findOne(UserDAO::id eq userId)

        if (user != null) {
            val userPokemon = user.pokemon?.plus(pokemonId) ?: listOf(pokemonId)

            instance.updateOne(UserDAO::id eq userId, setValue(UserDAO::pokemon, userPokemon))
        }
    }

    fun catchWildPokemon(userId: String, pokemonId: String, itemId: String): String {
        val user = instance.findOne(UserDAO::id eq userId)
        val item = ItemCollection().getItemById(itemId)

        if (user != null && item != null) {
            val isPokeball = item.affect.contains(AffectAttributes.CAPTURE)
            if (isPokeball) {
                val incrementRate = item.affect.getValue(AffectAttributes.CAPTURE)
                val pokemon = PokemonCollection().catch(pokemonId, incrementRate)

                if (pokemon != null) {
                    user.items?.forEach {
                        if (it.id == itemId) {
                            it.amount -= 1
                        }
                    }

                    instance.updateOne(UserDAO::id eq userId, setValue(UserDAO::items, user.items))

                    if (pokemon.status == PokemonStatus.OWNED) {
                        getActivityRewards(userId, Activity.CATCH)
                        return "Congratulations, you have a new pokemon."
                    }
                }
            }
        }

        return "The pokemon has not captured yet."
    }

    fun battle(userId: String, pokemonId: String, wildPokemonId: String): BattleResultModel {
        val user = instance.findOne(UserDAO::id eq userId)
        val wildPokemon = PokemonCollection().getPokemonById(wildPokemonId)
        val pokemon = PokemonCollection().getPokemonById(pokemonId)
        val result: BattleResultModel

        // User not found
        if (user == null) return BattleResultModel(false, "User not found")
        // Pokemon Not found
        if (wildPokemon == null) return BattleResultModel(false, "Wild Pokemon not found")
        // Not a wild pokemon
        if (wildPokemon.status != PokemonStatus.WILD) return BattleResultModel(false, "Not a wild pokemon")
        // Pokemon Not found
        if (pokemon == null) return BattleResultModel(false, "Send an invalid Pokemon")

        val isOwner = user.pokemon?.contains(pokemon.id) ?: false
        if (!isOwner) return BattleResultModel(false, "You can't choose this pokemon to fight")


        if (pokemon.power > wildPokemon.power) {
            // Increase pokemon exp
            val exp = wildPokemon.level * 100
            PokemonCollection().addExp(pokemonId, exp)

            val earnedItems = this.receiveRewards(user, pokemonId)
            getActivityRewards(userId, Activity.CATCH)
            result = BattleResultModel(true, "You won. Claim your rewards.", exp, earnedItems)
        } else {
            result = BattleResultModel(false, "You lost. Become stronger for the next time.")
        }

        // Remove wild Pokemon
        PokemonCollection().removePokemonById(wildPokemon.id)
        // Update user's pokemon
        user.pokemon = user.pokemon?.filter { it != wildPokemonId }
        instance.updateOne(UserDAO::id eq userId, setValue(UserDAO::pokemon, user.pokemon))

        return result
    }

    private fun receiveRewards(user: UserDAO, pokemonId: String): List<ItemModel> {
        // Random 3 items
        val randomReward = rewardItems.shuffled().take(3)

        randomReward.forEach {
            val alreadyHave = user.items?.find { i -> i.id == it.id }
            if (alreadyHave != null) {
                user.items?.forEach { i ->
                    if (i.id == it.id) {
                        i.amount += 1
                    }
                }
            } else {
                user.items = user.items?.plus(ItemModel(it.id, 1))
            }
        }

        // Add items
        instance.updateOne(UserDAO::id eq user.id, setValue(UserDAO::items, user.items))

        return randomReward.map { ItemModel(it.id, 1)}
    }
}