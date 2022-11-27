package com.example.util

import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(val message: String, val statusCode: Int){
}