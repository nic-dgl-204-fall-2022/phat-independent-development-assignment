package com.example.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.DAO.UserCollection
import io.ktor.server.application.*
import io.ktor.server.engine.*
import kotlinx.serialization.Serializable
import java.util.*
import kotlin.collections.HashMap

@Serializable
class LoginModel(private val username: String?, private val password: String?) {
    fun validate(): String? {
        if (username.isNullOrEmpty()) {
            return "Username is required."
        } else if(password.isNullOrEmpty()) {
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

    fun login(secret: String, issuer: String, audience: String): HashMap<String, String> {
            val token = JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("username", username)
                .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                .sign(Algorithm.HMAC256(secret))

        return hashMapOf("token" to token)
    }
}