package com.example.util

import java.util.*

fun capitalize(text: String) =
    text.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }