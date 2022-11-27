package com.example.plugins

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.auth.User
import com.example.auth.users
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
    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        authenticate("auth-jwt") {
            get("/profile") {

                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
            }
        }

        post("/login") {
            val user = call.receive<User>()
            // Check username and password
            // ...
            println(user.username)
            println(user.password)
            val userToVerify = users.first()
            val password = "1234"
            val hashedPw = BCrypt.withDefaults().hashToString(12, password.toCharArray())
            println(hashedPw)
            val result = BCrypt.verifyer().verify(user.password.toCharArray(), userToVerify.password)
            println(result)

            if (!result.verified) {
                return@post call.respondText("Username or Password is incorrect.", status = HttpStatusCode.Unauthorized)
            }

            val token = JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("username", user.username)
                .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                .sign(Algorithm.HMAC256(secret))

            call.respond(hashMapOf("token" to token))
        }
    }
}
