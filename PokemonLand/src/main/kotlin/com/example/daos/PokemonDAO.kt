package com.example.daos

import com.benasher44.uuid.uuid4
import com.example.item.rewardItems
import com.example.models.ItemModel
import com.example.util.Database
import com.example.util.QueryResult
import com.example.util.capitalize
import kotlinx.serialization.Serializable
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import org.litote.kmongo.setValue
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
    var status: PokemonStatus,
    var captureRate: Int? = null,
) {

}

class PokemonCollection() {
    private val instance = Database().instance.getCollection<PokemonDAO>("pokemon")

    fun initialize() {
        instance.drop()
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
                charmander,
                bulbasaur,
                squirtle,
                caterpie,
            )
        )
    }

    fun findWildPokemon(): PokemonDAO {
        val wildPokemon = wildPokemon.shuffled().first()
        instance.insertOne(wildPokemon)

        return wildPokemon
    }

    fun getPokemon(): List<PokemonDAO> {
        return instance.find().toList()
    }

    fun getPokemonById(id: String): PokemonDAO? {
        return instance.findOne(PokemonDAO::id eq id)
    }

    fun removePokemonById(id: String): PokemonDAO? {
        return instance.findOneAndDelete(PokemonDAO::id eq id)
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

    fun catch(id: String, captureRate: Int): PokemonDAO? {
        val pokemon = instance.findOne(PokemonDAO::id eq id)

        if (pokemon != null && captureRate > 0 && pokemon.status == PokemonStatus.WILD) {
            val currentRate = pokemon.captureRate?.plus(captureRate) ?: captureRate

            if (currentRate >= 100) {
                pokemon.status = PokemonStatus.OWNED
                pokemon.captureRate = null
            } else {
                pokemon.captureRate = currentRate
            }
            instance.replaceOne(PokemonDAO::id eq id, pokemon)
            return pokemon

        }

        return null
    }
}


val wildPokemon = mutableListOf<PokemonDAO>(
    pikachu,
    raichu,
    ivysaur,
    venusaur,
    charmeleon,
    charizard,
    wartortle,
    blastoise,
    metapod,
    butterfree
)