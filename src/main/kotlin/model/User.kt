package com.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String? = null,
    val user: String,
    val password: String
)