package com.example.item

import com.example.daos.UserCollection
import com.example.models.UseItemModel
import com.example.util.MessageResponse
import com.example.util.ObjectResponse
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.json

fun Route.getItems() {
    get("/item") {
        val items = ItemCollection().getItems()
        val response = ObjectResponse(data = items.json)
        call.respond(
            response
        )
    }

}

fun Route.getItemById() {
    get("/item/{id?}") {
        val itemId = call.parameters["id"] ?: return@get call.respond(MessageResponse("Not Found", 404))
        val item = ItemCollection().getItemById(itemId)
        if (item != null) {
            val response = ObjectResponse(data = item.json)
            call.respond(
                response
            )
        } else {
            call.respond(MessageResponse("Not found", 404))
        }
    }
}

fun Route.useItems() {
    post("/item/use") {
        val items = call.receive<List<UseItemModel>>()
        val principal = call.principal<JWTPrincipal>()
        val userId = principal!!.payload.getClaim("id").asString()

        UserCollection().useItem(userId, items)

        call.respond(MessageResponse("OK", 200))
    }
}