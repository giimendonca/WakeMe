package com.wakeme.app.domain.model

data class UserProfile(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val createdAt: java.util.Date? = null,
    val updatedAt: java.util.Date? = null
)

data class AuthUser(val uid: String, val name: String?, val email: String?)

sealed interface SessionState {
    data object Loading : SessionState
    data object SignedOut : SessionState
    data class SignedIn(val user: AuthUser) : SessionState
    data class ProfileError(val user: AuthUser, val message: String) : SessionState
}
