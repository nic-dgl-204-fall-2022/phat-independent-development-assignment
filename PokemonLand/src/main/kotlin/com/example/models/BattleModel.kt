package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class BattleModel(val pokemonId: String, val wildPokemonId: String) {
}