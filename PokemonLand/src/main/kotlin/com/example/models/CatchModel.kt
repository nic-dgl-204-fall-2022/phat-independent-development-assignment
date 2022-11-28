package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class CatchModel(val pokemonId: String, val itemId: String) {
}