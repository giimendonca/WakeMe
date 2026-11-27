package com.wakeme.app.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.FirebaseNetworkException
import com.wakeme.app.data.firebase.awaitResult
import com.wakeme.app.domain.model.AuthUser
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FirebaseAuthRepository(private val auth: FirebaseAuth) : AuthRepository {
    private val mutableSession = MutableStateFlow(auth.currentUser?.toDomain())
    override val session: StateFlow<AuthUser?> = mutableSession
    private val listener = FirebaseAuth.AuthStateListener { mutableSession.value = it.currentUser?.toDomain() }

    init { auth.addAuthStateListener(listener) }

    override suspend fun register(name: String, email: String, password: String): AuthUser {
        try {
            val user = auth.createUserWithEmailAndPassword(email.trim(), password).awaitResult().user
                ?: error("Não foi possível criar a conta.")
            return AuthUser(user.uid, name.trim(), user.email).also { mutableSession.value = it }
        } catch (error: Exception) { throw readable(error) }
    }

    override suspend fun signIn(email: String, password: String): AuthUser = try {
        auth.signInWithEmailAndPassword(email.trim(), password).awaitResult().user
            ?.toDomain()?.also { mutableSession.value = it } ?: error("Não foi possível entrar.")
    } catch (error: Exception) { throw readable(error) }

    override suspend fun sendPasswordReset(email: String) {
        try { auth.sendPasswordResetEmail(email.trim()).awaitResult() }
        catch (error: Exception) { throw readable(error) }
    }

    override suspend fun signOut() { auth.signOut(); mutableSession.value = null }
    override fun dispose() { auth.removeAuthStateListener(listener) }

    private fun readable(error: Exception): Exception {
        if (error is CancellationException) throw error
        return when (error) {
        is FirebaseAuthUserCollisionException -> Exception("Este e-mail já está cadastrado.")
        is FirebaseAuthInvalidCredentialsException, is FirebaseAuthInvalidUserException -> Exception("E-mail ou senha incorretos.")
        is FirebaseNetworkException -> Exception("Sem conexão. Verifique a internet e tente novamente.")
        else -> Exception("Não foi possível concluir. Confira os dados e tente novamente.")
        }
    }

    private fun com.google.firebase.auth.FirebaseUser.toDomain() = AuthUser(uid, displayName, email)
}
