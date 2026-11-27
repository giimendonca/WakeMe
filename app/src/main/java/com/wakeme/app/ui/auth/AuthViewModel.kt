package com.wakeme.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.wakeme.app.data.auth.AuthRepository
import com.wakeme.app.data.profile.ProfileRepository
import com.wakeme.app.domain.model.AuthUser
import com.wakeme.app.domain.model.SessionState
import com.wakeme.app.domain.model.UserProfile
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

    init {
        viewModelScope.launch {
            auth.session.collect { user ->
                if (user == null) {
                    mutableState.value = mutableState.value.copy(session = SessionState.SignedOut)
                } else if (!mutableState.value.busy && mutableState.value.session == SessionState.Loading) {
                    try {
                        profiles.ensure(UserProfile(user.uid, user.name.orEmpty(), user.email.orEmpty()))
                        mutableState.value = mutableState.value.copy(session = SessionState.SignedIn(user))
                    } catch (_: Exception) {
                        mutableState.value = mutableState.value.copy(session = SessionState.SignedOut,
                            message = "Não foi possível carregar o perfil. Verifique a conexão e tente entrar novamente.", messageIsError = true)
                    }
                }
            }
        }
    }

    fun register(name: String, email: String, password: String) {
        val validation = AuthFormValidation.validate(name, email, password, true)
        if (validation != null) return message(validation)
        perform {
            val user = auth.register(name.trim(), email.trim(), password)
            profiles.ensure(UserProfile(user.uid, name.trim(), user.email ?: email.trim()))
            mutableState.value = mutableState.value.copy(session = SessionState.SignedIn(user))
        }
    }

    fun signIn(email: String, password: String) {
        val validation = AuthFormValidation.validate("ok", email, password, false)
        if (validation != null) return message(validation)
        perform {
            val user = auth.signIn(email.trim(), password)
            profiles.ensure(UserProfile(user.uid, user.name.orEmpty(), user.email.orEmpty()))
            mutableState.value = mutableState.value.copy(session = SessionState.SignedIn(user))
        }
    }

    fun resetPassword(email: String) {
        if (AuthFormValidation.validate("ok", email, "ok", false) != null) return message("Informe um e-mail válido.")
        perform { auth.sendPasswordReset(email.trim()) }
    }

    fun signOut() = perform { auth.signOut() }
    fun clearMessage() { mutableState.value = mutableState.value.copy(message = null) }

    override fun onCleared() {
        auth.dispose()
        super.onCleared()
    }

    private fun perform(block: suspend () -> Unit) = viewModelScope.launch {
        mutableState.value = mutableState.value.copy(busy = true, message = null)
        try {
            block()
            mutableState.value = mutableState.value.copy(busy = false,
                message = "Se o e-mail estiver cadastrado, você receberá as instruções.", messageIsError = false)
        } catch (error: Exception) {
            mutableState.value = mutableState.value.copy(busy = false,
                message = if (error is FirebaseException) "Não foi possível salvar os dados. Verifique sua conexão e tente novamente."
                else error.message ?: "Ocorreu um erro.", messageIsError = true)
        }
    }
    private fun message(value: String) { mutableState.value = mutableState.value.copy(message = value, messageIsError = true) }

}

object AuthFormValidation {
    private val emailPattern = Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")
    fun validate(name: String, email: String, password: String, registering: Boolean): String? = when {
        registering && name.isBlank() -> "Informe seu nome."
        !emailPattern.matches(email.trim()) -> "Informe um e-mail válido."
        password.isBlank() -> "Informe sua senha."
        registering && password.length < 6 -> "A senha precisa ter pelo menos 6 caracteres."
        else -> null
    }
}
