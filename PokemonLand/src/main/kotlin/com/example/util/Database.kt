package com.example.util

import com.example.daos.PokemonCollection
import com.example.daos.PokemonDAO
import com.example.item.ItemCollection
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import de.sharpmind.ktor.EnvConfig
import io.ktor.server.application.*
import org.litote.kmongo.KMongo


data class Database(
    val client: MongoClient = KMongo.createClient(
        EnvConfig.getStringOrDefault(
            "DB_URI",
            "mongodb://localhost:27018"
        )
    ), //get com.mongodb.MongoClient new instance
    val instance: MongoDatabase = client.getDatabase(
        EnvConfig.getStringOrDefault(
            "DB_NAME",
            "pokemon-land2"
        )
    ) //normal java driver usage
) {

}