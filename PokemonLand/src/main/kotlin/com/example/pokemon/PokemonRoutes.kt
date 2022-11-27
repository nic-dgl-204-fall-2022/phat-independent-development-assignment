package com.example.pokemon

import com.example.daos.PokemonCollection
import com.example.util.ObjectResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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
}