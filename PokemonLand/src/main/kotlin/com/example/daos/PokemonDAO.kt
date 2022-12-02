package com.example.daos

import com.benasher44.uuid.uuid4
import com.example.util.Database
import kotlinx.serialization.Serializable
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import kotlin.math.roundToInt

enum class PokemonStatus {
    OWNED, WILD
}

@Serializable
data class PokemonDAO(
    var id: String,
    val name: String,
    val type: List<String>,
    var power: Int = 0,
    var level: Int = 1,
    var expPoints: Int = 0,
    var maxExpPoints: Int = 0, // Increase 40% after level up
    val imgName: String? = null,
    var status: PokemonStatus,
    var captureRate: Int? = null,
) {

}

class PokemonCollection() {
    private val instance = Database().instance.getCollection<PokemonDAO>("pokemon")

    fun initialize() {
        instance.drop()

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
        wildPokemon.id = uuid4().toString()
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
                pokemon.expPoints -= pokemon.maxExpPoints
                pokemon.maxExpPoints += (pokemon.expPoints * 0.5).roundToInt()
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