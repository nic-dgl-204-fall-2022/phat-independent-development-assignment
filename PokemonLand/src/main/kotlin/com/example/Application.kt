package com.example

import com.example.daos.PokemonCollection
import com.example.item.ItemCollection
import io.ktor.server.application.*
import com.example.plugins.*
import de.sharpmind.ktor.EnvConfig

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun initialData() {
    PokemonCollection().initialize()
    ItemCollection().initialize()
}

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    EnvConfig.initConfig(environment.config)
    configureSerialization()
    configureSecurity()
    configureRouting()
    initialData()
}
