package com.wakeme.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wakeme.app.data.auth.AuthRepository
import com.wakeme.app.data.profile.ProfileRepository
import com.wakeme.app.domain.model.AuthUser
import com.wakeme.app.domain.model.SessionState
import com.wakeme.app.domain.model.UserProfile
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val session: SessionState = SessionState.Loading,
    val busy: Boolean = false,
    val message: String? = null,
    val messageIsError: Boolean = false
)

class AuthViewModel(private val auth: AuthRepository, private val profiles: ProfileRepository) : ViewModel() {
    private val mutableState = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = mutableState.asStateFlow()
    private var pendingProfile: UserProfile? = null

    init {
        viewModelScope.launch {
            auth.session.collect { user ->
                if (user == null) {
                    pendingProfile = null
                    mutableState.value = mutableState.value.copy(session = SessionState.SignedOut)
                } else if (mutableState.value.session == SessionState.Loading && !mutableState.value.busy) {
                    val profile = user.toProfile()
                    pendingProfile = profile
                    loadProfile(user, profile)
                }
            }
        }
    }

    fun register(name: String, email: String, password: String, passwordConfirmation: String = password) {
        val validation = AuthFormValidation.validate(name, email, password, true, passwordConfirmation)
        if (validation != null) return message(validation)
        authenticate {
            val user = auth.register(name.trim(), email.trim(), password)
            completeProfile(user, UserProfile(user.uid, name.trim(), user.email ?: email.trim()))
        }
    }

    fun signIn(email: String, password: String) {
        val validation = AuthFormValidation.validate("ok", email, password, false)
        if (validation != null) return message(validation)
        authenticate {
            val user = auth.signIn(email.trim(), password)
            completeProfile(user, user.toProfile())
        }
    }

    fun retryProfileLoad() {
        val profile = pendingProfile ?: return
        val user = (mutableState.value.session as? SessionState.ProfileError)?.user ?: return
        viewModelScope.launch {
            mutableState.value = mutableState.value.copy(session = SessionState.Loading, busy = true, message = null)
            loadProfile(user, profile)
        }
    }

    fun resetPassword(email: String) {
        if (AuthFormValidation.validate("ok", email, "ok", false) != null) return message("Informe um e-mail válido.")
        viewModelScope.launch {
            mutableState.value = mutableState.value.copy(busy = true, message = null)
            try {
                auth.sendPasswordReset(email.trim())
                mutableState.value = mutableState.value.copy(busy = false,
                    message = "Se o e-mail estiver cadastrado, você receberá as instruções.", messageIsError = false)
            } catch (error: Exception) {
                if (error is CancellationException) throw error
                mutableState.value = mutableState.value.copy(busy = false, message = error.message ?: "Ocorreu um erro.", messageIsError = true)
            }
        }
    }

    fun signOut() = viewModelScope.launch {
        mutableState.value = mutableState.value.copy(busy = true, message = null)
        try {
            auth.signOut()
            pendingProfile = null
            mutableState.value = mutableState.value.copy(session = SessionState.SignedOut, busy = false)
        } catch (error: Exception) {
            if (error is CancellationException) throw error
            val currentUser = auth.session.value
            val currentSession = if (currentUser == null) SessionState.SignedOut
            else SessionState.ProfileError(currentUser, "Não foi possível sair da conta. Tente novamente.")
            mutableState.value = mutableState.value.copy(session = currentSession, busy = false,
                message = error.message ?: "Não foi possível sair da conta.", messageIsError = true)
        }
    }

    fun clearMessage() { mutableState.value = mutableState.value.copy(message = null) }

    override fun onCleared() {
        auth.dispose()
        super.onCleared()
    }

    private fun authenticate(block: suspend () -> Unit) = viewModelScope.launch {
        mutableState.value = mutableState.value.copy(busy = true, message = null)
        try {
            block()
        } catch (error: Exception) {
            if (error is CancellationException) throw error
            val currentUser = auth.session.value
            val session = when {
                currentUser == null -> SessionState.SignedOut
                mutableState.value.session is SessionState.ProfileError -> mutableState.value.session
                else -> SessionState.ProfileError(currentUser, "Não foi possível carregar o perfil. Tente novamente.")
            }
            mutableState.value = mutableState.value.copy(session = session, busy = false,
                message = error.message ?: "Não foi possível concluir. Verifique a conexão e tente novamente.", messageIsError = true)
        }
    }

    private suspend fun completeProfile(user: AuthUser, profile: UserProfile) {
        pendingProfile = profile
        loadProfile(user, profile)
    }

    private suspend fun loadProfile(user: AuthUser, profile: UserProfile) {
        try {
            profiles.ensure(profile)
            pendingProfile = null
            mutableState.value = mutableState.value.copy(session = SessionState.SignedIn(user), busy = false,
                message = null, messageIsError = false)
        } catch (error: Exception) {
            if (error is CancellationException) throw error
            pendingProfile = profile
            val explanation = "Não foi possível carregar o perfil. Verifique a conexão e tente novamente."
            mutableState.value = mutableState.value.copy(
                session = SessionState.ProfileError(user, explanation), busy = false,
                message = explanation, messageIsError = true
            )
        }
    }

    private fun AuthUser.toProfile() = UserProfile(uid, name.orEmpty(), email.orEmpty())

    private fun message(value: String) {
        mutableState.value = mutableState.value.copy(message = value, messageIsError = true)
    }
}

object AuthFormValidation {
    private val emailPattern = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")
    fun validate(
        name: String,
        email: String,
        password: String,
        registering: Boolean,
        passwordConfirmation: String? = null
    ): String? = when {
        registering && name.isBlank() -> "Informe seu nome."
        !emailPattern.matches(email.trim()) -> "Informe um e-mail válido."
        password.isBlank() -> "Informe sua senha."
        registering && password.length < 6 -> "A senha precisa ter pelo menos 6 caracteres."
        registering && passwordConfirmation != null && password != passwordConfirmation -> "As senhas não coincidem."
        else -> null
    }
}
