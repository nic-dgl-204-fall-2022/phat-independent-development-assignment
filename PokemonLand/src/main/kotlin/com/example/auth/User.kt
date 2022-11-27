package com.example.auth

import kotlinx.serialization.Serializable

@Serializable
data class User(val username: String, val password: String){
}

val users = listOf<User>(
    User(username = "phattran", password = "\$2a\$12\$jM0CVX.yiaDWAohrQlS1guqIucWqfn2hiRGhaY0hn8qOV3otQbLcC")
)