package com.example.DAO

import at.favre.lib.crypto.bcrypt.BCrypt
import com.benasher44.uuid.uuid4
import com.example.util.Database
import com.example.util.QueryResult
import com.mongodb.client.result.UpdateResult
import kotlinx.serialization.Serializable
import org.litote.kmongo.*

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

    fun getUserById(id: String): UserDAO? {
        return instance.findOne(UserDAO::id eq id)
    }

    fun getUserByUsername(username: String): UserDAO? {
        return instance.findOne(UserDAO::username eq username)
    }

    fun updateJwtToken(id: String, newJwtToken: String): UpdateResult {
        return instance.updateOne(UserDAO::id eq id, setValue(UserDAO::jwtToken, newJwtToken))
    }

    fun getUserByJwtToken(jwtToken: String): UserDAO? {
        return instance.findOne(UserDAO::jwtToken eq jwtToken)
    }
}