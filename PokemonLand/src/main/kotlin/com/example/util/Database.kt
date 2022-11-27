package com.example.util

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import io.ktor.server.application.*
import org.litote.kmongo.KMongo


data class Database(
    val client: MongoClient = KMongo.createClient("mongodb://localhost:27017"), //get com.mongodb.MongoClient new instance
    val instance: MongoDatabase = client.getDatabase("pokemon-land") //normal java driver usage
) {
}