package com.example.auth

import com.example.daos.UserCollection
import com.example.util.QueryResult
import kotlinx.serialization.Serializable

@Serializable
class SignupModel(private val username: String? = null, private val password: String? = null, private val confirm: String? = null) {
    fun validate(): String? {
        if (username.isNullOrEmpty()) {
            return "Username is required."
        } else if(password.isNullOrEmpty()) {
            return "Password is required."
        } else if (confirm.isNullOrEmpty()) {
            return "Confirm is required."
        }

        if (username.length !in 6..30) {
            return "Username must contain from 6 to 30 characters"
        } else if (!"^[a-zA-Z0-9_.-]*\$".toRegex().matches(username)) {
            return "Username must contain only numbers and letters."
        }

        if (password != confirm) {
            return "Password does not match Confirm"
        }

        return null
    }

    fun signup(): QueryResult {
        return if (!username.isNullOrEmpty() && !password.isNullOrEmpty()) {
            UserCollection().insertOne(username, password)
        } else {
            QueryResult(false, "Username and Password are required.")
        }
    }
}