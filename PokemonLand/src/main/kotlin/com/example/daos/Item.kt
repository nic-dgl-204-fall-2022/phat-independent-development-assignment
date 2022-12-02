package com.example.daos

import com.example.item.AffectAttributes
import com.example.item.ItemDAO
import com.example.item.ItemTypes

// Pokeball
val StandardPokeball = ItemDAO(
    "0c7a63c6-7598-49ad-8c2c-1f86f81f2ba5",
    "Standard Pokeball",
    ItemTypes.Pokeball,
    "Throw it to wild pokemon and it will capture it for you.",
    hashMapOf(AffectAttributes.CAPTURE to 50),
    imgName = "standard-pokeball.png"
)

val ElectricPokeBall = ItemDAO(
    "e99ac306-f8ae-43b4-a0fa-28c263a20709",
    "Electric Pokeball",
    ItemTypes.Pokeball,
    "Throw it to wild pokemon and it will capture it for you.",
    hashMapOf(AffectAttributes.CAPTURE to 100),
    imgName = "electric-pokeball.png"
)

// Consumable Items
val Apple = ItemDAO(
    "b6eaa392-05eb-4a5f-9d91-83f55fb731e2",
    "Apple",
    ItemTypes.ConsumableItems,
    "Let your pokemon take a bite and it will increase its EXP points.",
    hashMapOf(AffectAttributes.EXP to 100),
    imgName = "apple.png"
)
val Burger = ItemDAO(
    "0f9c405d-82bc-485a-a27b-78b459c7d614",
    "Burger",
    ItemTypes.ConsumableItems,
    "Let your pokemon take a bite and it will increase its EXP points.",
    hashMapOf(AffectAttributes.EXP to 200),
    imgName = "burger.png"
)

// Mystic Items
val ElectricMedal = ItemDAO(
    "f23b65c8-384e-407e-964c-44d76ed82d22",
    "Electric Medal",
    ItemTypes.MysticItems,
    "It increases your pokemon power",
    hashMapOf(AffectAttributes.POWER to 1000),
    imgName = "electric-medal.png"
)
val FireMedal = ItemDAO(
    "6fd32d27-720e-4a64-b18e-1024c1953951",
    "Fire Medal",
    ItemTypes.MysticItems,
    "It increases your pokemon power",
    hashMapOf(AffectAttributes.POWER to 1000),
    imgName = "fire-medal.png"
)
val GrassMedal = ItemDAO(
    "112c9eac-895f-4346-9109-cfcd8a640738",
    "Grass Medal",
    ItemTypes.MysticItems,
    "It increases your pokemon power",
    hashMapOf(AffectAttributes.POWER to 1000),
    imgName = "grass-medal.png"
)