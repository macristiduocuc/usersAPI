package com.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val user: String,
    val password: String
)