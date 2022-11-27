package com.example.pokemon

import com.example.daos.PokemonCollection
import com.example.util.MessageResponse
import com.example.util.ObjectResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.json

fun Route.getPokemon() {
    get("/pokemon") {
        val pokemon = PokemonCollection().getPokemon()
        val response = hashMapOf(
            "message" to "OK",
            "statusCode" to HttpStatusCode.OK.value,
            "data" to pokemon
        )
        call.respond(pokemon)
    }

    get("/pokemon/{id?}") {
        val pokemonId = call.parameters["id"] ?: return@get call.respond(MessageResponse("Not Found", 404))
        val pokemon = PokemonCollection().getPokemonById(pokemonId)
        val response = pokemon?.let { it1 -> ObjectResponse(data = it1) }
        call.respond(
            response!!.json
        )
    }
}