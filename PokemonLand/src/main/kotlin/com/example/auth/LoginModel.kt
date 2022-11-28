package com.example.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.daos.UserCollection
import de.sharpmind.ktor.EnvConfig
import io.ktor.server.application.*
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class LoginModel(private val username: String?, private val password: String?) {
    fun validate(): String? {
        println(Application::environment)
        if (username.isNullOrEmpty()) {
            return "Username is required."
        } else if (password.isNullOrEmpty()) {
            return "Password is required."
        }

        val unauthorizedErrorMsg = "Username or Password is incorrect."
        val user = UserCollection().getUserByUsername(username) ?: return unauthorizedErrorMsg
        val correctPassword = BCrypt.verifyer().verify(password.toCharArray(), user.hashedPassword)

        if (!correctPassword.verified) {
            return unauthorizedErrorMsg
        }

        return null
    }

    fun login(): String? {
        if (username == null) {
            return null
        }

        val user = UserCollection().getUserByUsername(username)
        var token: String? = null

        if (user != null) {
            token = JWT.create()
                .withAudience(EnvConfig.getString("JWT_AUDIENCE"))
                .withIssuer(EnvConfig.getString("JWT_ISSUER"))
                .withClaim("id", user.id)
                .withExpiresAt(Date(System.currentTimeMillis() + 60_000 * 60 * 24))
                .sign(Algorithm.HMAC256(EnvConfig.getString("JWT_SECRET")))

            UserCollection().updateJwtToken(user.id, token.toString())

        }

        return token
    }
}