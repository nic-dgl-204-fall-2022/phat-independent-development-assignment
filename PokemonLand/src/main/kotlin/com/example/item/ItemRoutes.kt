package com.example.item

import com.example.util.MessageResponse
import com.example.util.ObjectResponse
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.json

fun Route.getItems() {
    get("/item") {
        val items = ItemCollection().getItems()
        val response = ObjectResponse(data = items)
        call.respond(
            response.json
        )
    }

}

fun Route.getItemById() {
    get("/item/{id?}") {
        val itemId = call.parameters["id"] ?: return@get call.respond(MessageResponse("Not Found", 404))
        val item = ItemCollection().getItemById(itemId)
        val response = item?.let { it1 -> ObjectResponse(data = it1) }
        call.respond(
            response!!.json
        )
    }
}