package com.example.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.daos.UserCollection
import com.example.util.MessageResponse
import de.sharpmind.ktor.EnvConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureSecurity() {
    authentication {
        jwt("auth-jwt") {
            realm = EnvConfig.getString("JWT_REALM")

            verifier(
                JWT
                    .require(Algorithm.HMAC256(EnvConfig.getString("JWT_SECRET")))
                    .withAudience(EnvConfig.getString("JWT_AUDIENCE"))
                    .withIssuer(EnvConfig.getString("JWT_ISSUER"))
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(EnvConfig.getString("JWT_AUDIENCE"))) {
                    val userId = credential.payload.getClaim("id")?.asString()
                    if (userId != null) {
                        val user = UserCollection().getUserById(userId)
                        if (user?.jwtToken != null) {
                            JWTPrincipal(credential.payload)
                        } else null
                    } else null
                } else null
            }
            challenge { defaultScheme, realm ->
                call.respond(MessageResponse("Token is not valid or has expired", 401))
            }
        }
    }

}
