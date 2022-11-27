package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.benasher44.uuid.uuid4
import com.example.DAO.UserDAO
import com.example.auth.getProfile
import com.example.auth.login
import com.example.auth.signup
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import java.util.*


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
            }
        }
    }
}
