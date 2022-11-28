package com.example.daos

import com.benasher44.uuid.uuid4
import com.example.util.Database
import com.example.util.QueryResult
import com.example.util.capitalize
import kotlinx.serialization.Serializable
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import java.util.*
import kotlin.math.roundToInt

enum class PokemonStatus {
    OWNED, WILD
}

@Serializable
data class PokemonDAO(
    val id: String,
    val name: String,
    val type: List<String>,
    var power: Int = 0,
    var level: Int = 1,
    var expPoints: Int = 0,
    var maxExpPoints: Int = 0, // Increase 40% after level up
    var evolutions: List<HashMap<String, String>>? = null,
    val imgName: String? = null,
    val status: PokemonStatus,
    val captureRate: Int? = null,
) {
}

class PokemonCollection() {
    private val instance = Database().instance.getCollection<PokemonDAO>("pokemon")

    fun initialize() {
        instance.drop()
        val pichu = PokemonDAO(
            "89cd54fa-6744-4cb0-b42b-50ceadb5a28e",
            "Pichu",
            listOf("Electric"),
            level = 10,
            maxExpPoints = 1000,
            status = PokemonStatus.OWNED
        )
        val pikachu = PokemonDAO(
            "b6800af1-6076-4cf9-a74d-266d78eb7fec",
            "Pikachu",
            listOf("Electric"),
            level = 16,
            maxExpPoints = 1500,
            status = PokemonStatus.OWNED
        )
        val raichu = PokemonDAO(
            "52830674-de5f-4951-8f09-8803454d4b5c",
            "Raichu",
            listOf("Electric"),
            level = 32,
            maxExpPoints = 3500,
            status = PokemonStatus.OWNED
        )
        val charmander = PokemonDAO(
            "13b55d90-fb4e-4a36-b4e9-044d4ddccf07",
            "Charmander",
            listOf("Fire"),
            level = 10,
            maxExpPoints = 1000,
            status = PokemonStatus.OWNED
        )
        val bulbasaur =
            PokemonDAO(
                "ec51b4d0-9e72-40be-a130-3062a5e37dd3",
                "Bulbasaur",
                listOf("Grass", "Poison"),
                level = 10,
                maxExpPoints = 1000,
                status = PokemonStatus.OWNED
            )
        val squirtle = PokemonDAO(
            "9a688086-4dfe-48a8-9d97-929b95fb7aaf",
            "Squirtle",
            listOf("Water"),
            level = 10,
            maxExpPoints = 1000,
            status = PokemonStatus.OWNED
        )
        val caterpie = PokemonDAO(
            "82cdda8a-fc69-488b-945e-8210dae43a82",
            "Caterpie",
            listOf("Bug"),
            level = 10,
            maxExpPoints = 1000,
            status = PokemonStatus.OWNED
        )

        val pichuEvolutions = listOf(
            hashMapOf(
                "pokemonId" to pichu.id,
                "levelRange" to "1-16"
            ),
            hashMapOf(
                "pokemonId" to pikachu.id,
                "levelRange" to "16-32"
            ),
            hashMapOf(
                "pokemonId" to raichu.id,
                "levelRange" to "32+"
            ),
        )
        pichu.evolutions = pichuEvolutions
        pikachu.evolutions = pichuEvolutions
        pichu.evolutions = pichuEvolutions

        instance.insertMany(
            mutableListOf(
                pichu,
                pikachu,
                raichu,
                charmander,
                bulbasaur,
                squirtle,
                caterpie,
            )
        )
    }

    fun insertOne(name: String, type: List<String>): QueryResult {
        val id = uuid4().toString()

        val existedPokemon = instance.findOne(PokemonDAO::name eq name)
        if (existedPokemon != null) {
            return QueryResult(false, "Name is already taken.")
        }

        val insertResult = QueryResult(false)
        try {
            val pokemon = PokemonDAO(
                id,
                capitalize(name),
                type.map { it -> capitalize(it) },
                status = PokemonStatus.WILD,
                captureRate = 0
            )

            instance.insertOne(pokemon)
            insertResult.done = true
            insertResult.data = id
        } catch (e: Exception) {
            insertResult.error = e.message
        }

        return insertResult
    }

    fun getPokemon(): List<PokemonDAO> {
        return instance.find().toList()
    }

    fun getPokemonById(id: String): PokemonDAO? {
        return instance.findOne(PokemonDAO::id eq id)
    }

    fun addExp(id: String, expPoints: Int) {
        val pokemon = instance.findOne(PokemonDAO::id eq id)

        if (pokemon != null && expPoints > 0) {
            pokemon.expPoints += expPoints

            if (pokemon.expPoints >= pokemon.maxExpPoints) {
                pokemon.level += 1
                pokemon.maxExpPoints += (pokemon.expPoints * 0.4).roundToInt()
                pokemon.expPoints -= pokemon.maxExpPoints
            }

            instance.replaceOne(PokemonDAO::id eq id, pokemon)
        }
    }

    fun addPower(id: String, powerPoints: Int) {
        val pokemon = instance.findOne(PokemonDAO::id eq id)

        if (pokemon != null && powerPoints > 0) {
            pokemon.power += powerPoints

            instance.replaceOne(PokemonDAO::id eq id, pokemon)
        }
    }
}