package com.example.plugins

import io.ktor.server.auth.*
import io.ktor.util.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.DAO.UserCollection
import de.sharpmind.ktor.EnvConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

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
                if (credential.payload.audience.contains(EnvConfig.getString("JWT_AUDIENCE"))) JWTPrincipal(credential.payload) else null
            }
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }

}
