package com.wakeme.app.data.profile

import com.wakeme.app.domain.model.UserProfile

interface ProfileRepository {
    suspend fun ensure(profile: UserProfile)
    suspend fun get(uid: String): UserProfile?
}
