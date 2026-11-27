package com.wakeme.app.data.auth

import com.wakeme.app.domain.model.AuthUser
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val session: StateFlow<AuthUser?>
    suspend fun register(name: String, email: String, password: String): AuthUser
    suspend fun signIn(email: String, password: String): AuthUser
    suspend fun sendPasswordReset(email: String)
    suspend fun signOut()
    fun dispose() = Unit
}
