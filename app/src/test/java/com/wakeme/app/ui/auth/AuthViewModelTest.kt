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
import org.junit.Test

class AuthViewModelTest {
    @Test fun loginPublishesSessionOnlyAfterProfileIsReady() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val repository = FakeAuthRepository()
            val profiles = FakeProfileRepository()
            val viewModel = AuthViewModel(repository, profiles)

            viewModel.signIn("ana@example.com", "secret1")
            advanceUntilIdle()

            assertEquals(SessionState.SignedIn(AuthUser("uid-1", "Ana", "ana@example.com")), viewModel.state.value.session)
            assertEquals(1, profiles.ensureCalls)
        } finally { Dispatchers.resetMain() }
    }

    @Test fun failedProfileWriteDoesNotOpenAuthenticatedNavigation() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        try {
            val viewModel = AuthViewModel(FakeAuthRepository(), FakeProfileRepository(fail = true))

            viewModel.signIn("ana@example.com", "secret1")
            advanceUntilIdle()

            assertEquals(SessionState.SignedOut, viewModel.state.value.session)
            assertEquals(true, viewModel.state.value.messageIsError)
        } finally { Dispatchers.resetMain() }
    }

    private class FakeAuthRepository : AuthRepository {
        override val session = MutableStateFlow<AuthUser?>(null)
        override suspend fun register(name: String, email: String, password: String) = AuthUser("uid-1", name, email)
        override suspend fun signIn(email: String, password: String) = AuthUser("uid-1", "Ana", email).also { session.value = it }
        override suspend fun sendPasswordReset(email: String) = Unit
        override suspend fun signOut() { session.value = null }
    }

    private class FakeProfileRepository(private val fail: Boolean = false) : ProfileRepository {
        var ensureCalls = 0
        override suspend fun ensure(profile: UserProfile) {
            ensureCalls++
            if (fail) throw IllegalStateException("profile unavailable")
        }
        override suspend fun get(uid: String): UserProfile? = null
    }
}
