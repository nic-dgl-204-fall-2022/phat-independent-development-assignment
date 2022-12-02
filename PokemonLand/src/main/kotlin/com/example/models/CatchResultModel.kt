package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class CatchResultModel(var captured: Boolean = false, var message: String = "") {
}