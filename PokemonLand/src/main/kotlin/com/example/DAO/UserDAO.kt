package com.example.DAO

import at.favre.lib.crypto.bcrypt.BCrypt
import com.benasher44.uuid.uuid4
import com.example.util.Database
import com.example.util.QueryResult
import kotlinx.serialization.Serializable
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection

@Serializable
data class UserDAO(
    val id: String,
    val username: String,
    val hashedPassword: String,
    val level: Int? = null,
    val experiencePoints: Int? = null,
    val jwtToken: String? = null
) {
}

class UserCollection() {
    private val instance = Database().instance.getCollection<UserDAO>("users") //KMongo extension method

    fun insertOne(username: String, password: String): QueryResult {
        val id = uuid4().toString()
        val hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray())

        // Check to see if the username already existed.
        val existedUser = instance.findOne(UserDAO::username eq username.lowercase())
        if (existedUser != null) {
            return QueryResult(false, "Username is already taken.")
        }

        val insertResult = QueryResult(false)
        try {
            val user = UserDAO(id, username.lowercase(), hashedPassword, level = 1, experiencePoints = 0)
            val result = instance.insertOne(user)
            insertResult.done = true
            insertResult.data = id
        } catch (e: Exception){
            insertResult.error = e.message
        }

        return insertResult
    }

    fun getUserByUsername(username: String): UserDAO? {
        return instance.findOne(UserDAO::username eq username)
    }
}