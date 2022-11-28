package com.example.plugins

import com.example.auth.getProfile
import com.example.auth.login
import com.example.auth.logout
import com.example.auth.signup
import com.example.explore.battle
import com.example.explore.catchWildPokemon
import com.example.explore.findWildPokemon
import com.example.item.getItemById
import com.example.item.getItems
import com.example.item.useItems
import com.example.pokemon.getPokemon
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureRouting() {
    routing {
        route("/api") {
            get("/") {
                call.respond("Pokemon Land")
            }

            // Auth
            signup()
            login()
            //Pokemon
            getPokemon()
            // Items
            getItems()

            authenticate("auth-jwt") {
                getProfile()
                logout()

                // Items
                getItemById()
                useItems()

                // Explore
                findWildPokemon()
                catchWildPokemon()
                battle()
            }
        }
    }
}
