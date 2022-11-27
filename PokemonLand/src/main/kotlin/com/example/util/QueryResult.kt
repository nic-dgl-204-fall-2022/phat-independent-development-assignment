package com.example.util

import kotlinx.serialization.Serializable

@Serializable
data class QueryResult(var done: Boolean, var error: String? = null, var data: String? = null) {
}