package com.example.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginModel(val username: String, val password: String) {

}