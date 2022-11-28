package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class UseItemModel(val id: String, var amount: Int, val pokemonId: String) {
}