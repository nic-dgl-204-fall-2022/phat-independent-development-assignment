package com.example.plugins

import com.example.auth.getProfile
import com.example.auth.login
import com.example.auth.logout
import com.example.auth.signup
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

            signup()
            login()

            authenticate("auth-jwt") {
                getProfile()
                logout()
            }
        }
    }
}
