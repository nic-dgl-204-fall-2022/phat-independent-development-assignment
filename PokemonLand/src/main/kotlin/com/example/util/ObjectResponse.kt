package com.example.util

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class ObjectResponse(
    val message: String = "OK",
    val statusCode: Int = HttpStatusCode.OK.value,
    val data: HashMap<String, String?>
) {
}