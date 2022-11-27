package com.example.auth

import com.example.util.MessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.signup() {
    post("/signup") {
        try {
            val signUpInput = call.receive<SignupModel>()
            val error = signUpInput.validate()
            if (error != null) {
                val message = MessageResponse(error, HttpStatusCode.BadRequest.value)
                return@post call.respond(message)
            }

            val result = signUpInput.signup()

            call.respond(result)
        } catch (e: Exception) {
            call.respond(
                hashMapOf(
                    "error" to e.message,
                    "statusCode" to HttpStatusCode.InternalServerError.value
                )
            )
        }
    }
}

fun Route.login(secret: String, issuer: String, audience: String) {
    post("/login") {
        try {
            val loginInput = call.receive<LoginModel>()
            val error = loginInput.validate()
            if (error != null) {
                val message = MessageResponse(error, HttpStatusCode.Unauthorized.value)
                return@post call.respond(message)
            }

            val result = loginInput.login(secret, issuer, audience)
            call.respond(result)
        } catch (e: Exception) {
            call.respond(
                hashMapOf(
                    "error" to e.message,
                    "statusCode" to HttpStatusCode.InternalServerError.value
                )
            )
        }
    }
}

fun Route.getProfile() {
    get("/profile") {
        val principal = call.principal<JWTPrincipal>()
        val username = principal!!.payload.getClaim("username").asString()
        val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
        call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
    }
}

fun Route.logout() {
    post("/logout") {

    }
}