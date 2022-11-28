package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class ItemModel(val id: String, var amount: Int) {
}