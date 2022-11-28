package com.example.models

import com.example.item.ItemDAO
import kotlinx.serialization.Serializable

@Serializable
data class BattleResultModel(
    val won: Boolean,
    val message: String,
    val earnedExp: Int = 0,
    val earnedItems: List<ItemModel>? = null
) {
}