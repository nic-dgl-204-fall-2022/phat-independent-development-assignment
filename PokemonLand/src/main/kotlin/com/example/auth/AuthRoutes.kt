package com.example.auth

import com.example.util.MessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
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
            println(e)
            call.respondText(
                text = e.message ?: "Server Error",
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.InternalServerError
            )
        }
    }
}

fun Route.login() {
    post("/login") {

    }
}

fun Route.getProfile() {
    get("/profile") {

    }
}

fun Route.logout() {
    post("/logout") {

    }
}