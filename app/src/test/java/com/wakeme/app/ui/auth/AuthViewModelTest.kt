package com.wakeme.app.ui.auth

import com.wakeme.app.data.auth.AuthRepository
import com.wakeme.app.data.profile.ProfileRepository
import com.wakeme.app.domain.model.AuthUser
import com.wakeme.app.domain.model.SessionState
import com.wakeme.app.domain.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AuthViewModelTest {
    @Test fun loginWithExistingProfileAndMissingDisplayNameCompletesSession() = runTest {
        withMainDispatcher {
            val auth = FakeAuthRepository()
            val profiles = FakeProfileRepository()
            val viewModel = AuthViewModel(auth, profiles)

            viewModel.signIn("ana@example.com", "secret1")
            advanceUntilIdle()

            assertEquals(SessionState.SignedIn(AuthUser("uid-1", null, "ana@example.com")), viewModel.state.value.session)
            assertEquals("", profiles.lastProfile?.name)
            assertEquals(1, profiles.ensureCalls)
        }
    }

    @Test fun newRegistrationUsesNameEnteredInForm() = runTest {
        withMainDispatcher {
            val profiles = FakeProfileRepository()
            val viewModel = AuthViewModel(FakeAuthRepository(), profiles)

            viewModel.register("Ana Silva", "ana@example.com", "secret1")
            advanceUntilIdle()

            assertEquals("Ana Silva", profiles.lastProfile?.name)
            assertTrue(viewModel.state.value.session is SessionState.SignedIn)
        }
    }

    @Test fun mismatchedPasswordConfirmationDoesNotCallAuthenticationRepository() = runTest {
        withMainDispatcher {
            val auth = FakeAuthRepository()
            val viewModel = AuthViewModel(auth, FakeProfileRepository())

            viewModel.register("Ana", "ana@example.com", "secret1", "secret2")
            advanceUntilIdle()

            assertEquals(0, auth.registerCalls)
            assertEquals("As senhas não coincidem.", viewModel.state.value.message)
        }
    }

    @Test fun firestoreNetworkFailureKeepsAuthenticatedUserAndCanRecover() = runTest {
        withMainDispatcher {
            val auth = FakeAuthRepository()
            val profiles = FakeProfileRepository(failuresRemaining = 1)
            val viewModel = AuthViewModel(auth, profiles)

            viewModel.signIn("ana@example.com", "secret1")
            advanceUntilIdle()

            assertTrue(viewModel.state.value.session is SessionState.ProfileError)
            assertEquals("uid-1", auth.session.value?.uid)

            viewModel.retryProfileLoad()
            advanceUntilIdle()

            assertEquals(SessionState.SignedIn(AuthUser("uid-1", null, "ana@example.com")), viewModel.state.value.session)
            assertEquals(2, profiles.ensureCalls)
        }
    }

    @Test fun startupRestoresPersistedAuthSessionOnlyAfterProfileLoads() = runTest {
        withMainDispatcher {
            val persistedUser = AuthUser("uid-1", null, "ana@example.com")
            val auth = FakeAuthRepository(initialUser = persistedUser)
            val profiles = FakeProfileRepository()
            val viewModel = AuthViewModel(auth, profiles)

            advanceUntilIdle()

            assertEquals(SessionState.SignedIn(persistedUser), viewModel.state.value.session)
            assertEquals(1, profiles.ensureCalls)
        }
    }

    @Test fun logoutClearsSession() = runTest {
        withMainDispatcher {
            val auth = FakeAuthRepository(initialUser = AuthUser("uid-1", "Ana", "ana@example.com"))
            val viewModel = AuthViewModel(auth, FakeProfileRepository())
            advanceUntilIdle()

            viewModel.signOut()
            advanceUntilIdle()

            assertEquals(SessionState.SignedOut, viewModel.state.value.session)
            assertEquals(null, auth.session.value)
        }
    }

    private suspend fun kotlinx.coroutines.test.TestScope.withMainDispatcher(block: suspend () -> Unit) {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try { block() } finally { Dispatchers.resetMain() }
    }

    private class FakeAuthRepository(initialUser: AuthUser? = null) : AuthRepository {
        override val session = MutableStateFlow(initialUser)
        var registerCalls = 0
        override suspend fun register(name: String, email: String, password: String) =
            AuthUser("uid-1", null, email).also { registerCalls++; session.value = it }
        override suspend fun signIn(email: String, password: String) =
            AuthUser("uid-1", null, email).also { session.value = it }
        override suspend fun sendPasswordReset(email: String) = Unit
        override suspend fun signOut() { session.value = null }
    }

    private class FakeProfileRepository(private var failuresRemaining: Int = 0) : ProfileRepository {
        var ensureCalls = 0
        var lastProfile: UserProfile? = null
        override suspend fun ensure(profile: UserProfile) {
            ensureCalls++
            lastProfile = profile
            if (failuresRemaining > 0) {
                failuresRemaining--
                throw IllegalStateException("network unavailable")
            }
        }
        override suspend fun get(uid: String): UserProfile? = null
    }
}
