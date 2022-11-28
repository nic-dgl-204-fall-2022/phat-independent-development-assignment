package com.example.explore

import com.example.daos.PokemonCollection
import com.example.daos.UserCollection
import com.example.models.BattleModel
import com.example.models.CatchModel
import com.example.util.ObjectResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.json

fun Route.findWildPokemon() {
    post("/explore/find") {
        val principal = call.principal<JWTPrincipal>()
        val userId = principal!!.payload.getClaim("id").asString()
        val wildPokemon = PokemonCollection().findWildPokemon()
        UserCollection().foundWildPokemon(userId, wildPokemon.id)

        call.respond(wildPokemon);
    }
}

fun Route.catchWildPokemon() {
    put("/explore/catch") {
        val catchInput = call.receive<CatchModel>()
        val principal = call.principal<JWTPrincipal>()
        val userId = principal!!.payload.getClaim("id").asString()
        val result = UserCollection().catchWildPokemon(userId, catchInput.pokemonId, catchInput.itemId)

        call.respond(
            ObjectResponse(
                data = result
            ).json
        );
    }
}

fun Route.battle() {
    put("/explore/battle") {
        val battleInput = call.receive<BattleModel>()
        val principal = call.principal<JWTPrincipal>()
        val userId = principal!!.payload.getClaim("id").asString()
        val result = UserCollection().battle(userId, battleInput.pokemonId, battleInput.wildPokemonId)

        call.respond(
            ObjectResponse(
                data = result
            ).json
        );
    }
}