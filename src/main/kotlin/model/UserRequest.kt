package com.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val user: String,
    val password: String
)