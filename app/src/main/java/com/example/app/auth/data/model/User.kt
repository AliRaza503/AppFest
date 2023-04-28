package com.example.app.auth.data.model

import java.util.UUID

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class User(
    val userId: UUID?,
    val displayName: String,
    val email: String
)