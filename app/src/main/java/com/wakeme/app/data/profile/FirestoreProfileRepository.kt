package com.wakeme.app.data.profile

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.wakeme.app.data.firebase.awaitResult
import com.wakeme.app.domain.model.UserProfile

class FirestoreProfileRepository(private val firestore: FirebaseFirestore) : ProfileRepository {
    override suspend fun ensure(profile: UserProfile) {
        val ref = firestore.collection("users").document(profile.uid)
        val existing = ref.get().awaitResult().exists()
        val fields = mutableMapOf<String, Any>(
            "uid" to profile.uid, "email" to profile.email,
            "updatedAt" to com.google.firebase.firestore.FieldValue.serverTimestamp())
        if (profile.name.isNotBlank()) fields["name"] = profile.name
        else if (!existing) throw IllegalStateException("Não foi possível carregar o perfil da conta.")
        if (!existing) fields["createdAt"] = com.google.firebase.firestore.FieldValue.serverTimestamp()
        ref.set(fields, SetOptions.merge()).awaitResult()
    }

    override suspend fun get(uid: String): UserProfile? =
        firestore.collection("users").document(uid).get().awaitResult().toObject(UserProfile::class.java)
}
