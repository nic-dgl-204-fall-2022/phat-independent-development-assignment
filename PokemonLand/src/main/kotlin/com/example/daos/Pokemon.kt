package com.example.daos

import com.benasher44.uuid.uuid4

val pichu = PokemonDAO(
    "89cd54fa-6744-4cb0-b42b-50ceadb5a28e",
    "Pichu",
    listOf("Electric"),
    power = 15000,
    level = 10,
    maxExpPoints = 1000,
    status = PokemonStatus.OWNED,
    imgName = "pichu.svg"
)
val charmander = PokemonDAO(
    "13b55d90-fb4e-4a36-b4e9-044d4ddccf07",
    "Charmander",
    listOf("Fire"),
    power = 15000,
    level = 10,
    maxExpPoints = 1000,
    status = PokemonStatus.OWNED,
    imgName = "charmander.svg"
)
val bulbasaur =
    PokemonDAO(
        "ec51b4d0-9e72-40be-a130-3062a5e37dd3",
        "Bulbasaur",
        listOf("Grass", "Poison"),
        power = 15000,
        level = 10,
        maxExpPoints = 1000,
        status = PokemonStatus.OWNED,
        imgName = "bulbasaur.svg"
    )
val squirtle = PokemonDAO(
    "9a688086-4dfe-48a8-9d97-929b95fb7aaf",
    "Squirtle",
    listOf("Water"),
    power = 15000,
    level = 10,
    maxExpPoints = 1000,
    status = PokemonStatus.OWNED,
    imgName = "squirtle.svg"
)
val caterpie = PokemonDAO(
    "82cdda8a-fc69-488b-945e-8210dae43a82",
    "Caterpie",
    listOf("Bug"),
    power = 10000,
    level = 10,
    maxExpPoints = 1000,
    status = PokemonStatus.OWNED,
    imgName = "caterpie.svg"
)

// Random Pokemon with random Ids
val pikachu = PokemonDAO(
    uuid4().toString(),
    "Pikachu",
    listOf("Electric"),
    power = 25000,
    level = 16,
    maxExpPoints = 1500,
    status = PokemonStatus.WILD,
    captureRate = 0,
    imgName = "pikachu.svg"
)
val raichu = PokemonDAO(
    uuid4().toString(),
    "Raichu",
    listOf("Electric"),
    power = 45000,
    level = 32,
    maxExpPoints = 3500,
    status = PokemonStatus.WILD,
    captureRate = 0,
    imgName = "raichu.svg"
)
val ivysaur = PokemonDAO(
    uuid4().toString(),
    "Ivysaur",
    listOf("Grass", "Posion"),
    power = 15000,
    level = 15,
    maxExpPoints = 1500,
    status = PokemonStatus.WILD,
    captureRate = 0,
    imgName = "ivysaur.svg"
)
val venusaur = PokemonDAO(
    uuid4().toString(),
    "Venusaur",
    listOf("Grass", "Posion"),
    power = 40000,
    level = 40,
    maxExpPoints = 4000,
    status = PokemonStatus.WILD,
    captureRate = 0,
    imgName = "venusaur.svg"
)
val wartortle = PokemonDAO(
    uuid4().toString(),
    "Wartortle",
    listOf("Water"),
    power = 20000,
    level = 18,
    maxExpPoints = 2000,
    status = PokemonStatus.WILD,
    captureRate = 0,
    imgName = "wartortle.svg"
)

val charizard = PokemonDAO(
    uuid4().toString(),
    "Charizard",
    listOf("Fire", "Flying"),
    power = 50000,
    level = 50,
    maxExpPoints = 5500,
    captureRate = 0,
    status = PokemonStatus.WILD,
    imgName = "charizard.svg"
)

val blastoise = PokemonDAO(
    uuid4().toString(),
    "Blastoise",
    listOf("Water"),
    power = 55000,
    level = 50,
    maxExpPoints = 6500,
    captureRate = 0,
    status = PokemonStatus.WILD,
    imgName = "blastoise.svg"
)
val metapod = PokemonDAO(
    uuid4().toString(),
    "Metapod",
    listOf("Bug"),
    power = 15000,
    level = 15,
    maxExpPoints = 1500,
    captureRate = 0,
    status = PokemonStatus.WILD,
    imgName = "metapod.svg"
)
val butterfree = PokemonDAO(
    uuid4().toString(),
    "Butterfree",
    listOf("Bug", "Flying"),
    level = 20,
    power = 20000,
    maxExpPoints = 2000,
    captureRate = 0,
    status = PokemonStatus.WILD,
    imgName = "butterfree.svg"
)
val charmeleon = PokemonDAO(
    uuid4().toString(),
    "Charmeleon",
    listOf("Fire"),
    power = 20000,
    level = 19,
    maxExpPoints = 1900,
    captureRate = 0,
    status = PokemonStatus.WILD,
    imgName = "charmeleon.svg"
)