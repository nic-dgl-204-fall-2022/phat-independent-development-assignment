package com.example.explore

import com.example.daos.PokemonCollection
import com.example.daos.UserCollection
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.findWildPokemon() {
    post("/explore/find") {
        val principal = call.principal<JWTPrincipal>()
        val userId = principal!!.payload.getClaim("id").asString()
        val wildPokemon = PokemonCollection().findWildPokemon()
        UserCollection().foundPokemon(userId, wildPokemon.id)

        call.respond(wildPokemon);
    }
}