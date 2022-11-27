package com.example.auth

import com.example.daos.UserCollection
import com.example.util.MessageResponse
import com.example.util.ObjectResponse
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

fun Route.login() {
    post("/login") {
        try {
            val loginInput = call.receive<LoginModel>()
            val error = loginInput.validate()
            if (error != null) {
                val message = MessageResponse(error, HttpStatusCode.Unauthorized.value)
                return@post call.respond(message)
            }

            val result = loginInput.login()

            call.respond(
                hashMapOf(
                    "token" to result
                )
            )
        } catch (e: Exception) {
            println(e)
            call.respond(
                hashMapOf(
                    "error" to "Server Error",
                    "statusCode" to HttpStatusCode.InternalServerError.value
                )
            )
        }
    }
}

fun Route.getProfile() {
    get("/profile") {
        val principal = call.principal<JWTPrincipal>()
        val userId = principal!!.payload.getClaim("id").asString()

        val user = UserCollection().getUserById(userId)
            ?: return@get call.respond(MessageResponse("Not Found", statusCode = HttpStatusCode.NotFound.value))

        val response = ObjectResponse(
            data = hashMapOf(
                "id" to user.id,
                "username" to user.username,
                "level" to user.level.toString(),
                "experiencePoints" to user.experiencePoints.toString(),
                "jwtToken" to user.jwtToken,
                "name" to user.name,
                "email" to user.email,
                "phone" to user.phone,
                "coins" to user.coins.toString()
            )
        )

        call.respond(response)
    }
}

fun Route.logout() {
    post("/logout") {
        val principal = call.principal<JWTPrincipal>()
        val userId = principal!!.payload.getClaim("id").asString()
        UserCollection().updateJwtToken(userId, null)

        call.respond(MessageResponse("OK", HttpStatusCode.OK.value))
    }
}