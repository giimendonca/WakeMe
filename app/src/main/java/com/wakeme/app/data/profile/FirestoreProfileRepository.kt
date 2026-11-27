package com.wakeme.app.data.profile

import com.google.firebase.firestore.FirebaseFirestore
import com.wakeme.app.data.firebase.awaitResult
import com.wakeme.app.domain.model.UserProfile

class FirestoreProfileRepository(private val firestore: FirebaseFirestore) : ProfileRepository {
    override suspend fun ensure(profile: UserProfile) {
        val ref = firestore.collection("users").document(profile.uid)
        val snapshot = ref.get().awaitResult()
        ref.set(profileDocumentFields(profile, snapshot.data)).awaitResult()
    }

    override suspend fun get(uid: String): UserProfile? =
        firestore.collection("users").document(uid).get().awaitResult().toObject(UserProfile::class.java)
}

internal fun profileDocumentFields(profile: UserProfile, existing: Map<String, Any>?): Map<String, Any> {
    val uid: String
    val name: String
    val email: String
    val createdAt: Any

    if (existing == null) {
        uid = profile.uid
        name = profile.name.takeIf(String::isNotBlank)
            ?: throw IllegalStateException("O nome do perfil não pode ficar vazio.")
        email = profile.email.takeIf(String::isNotBlank)
            ?: throw IllegalStateException("O e-mail do perfil não pode ficar vazio.")
        createdAt = com.google.firebase.firestore.FieldValue.serverTimestamp()
    } else {
        uid = (existing["uid"] as? String)?.takeIf(String::isNotBlank)
            ?: throw IllegalStateException("O perfil existente não tem um UID válido.")
        name = (existing["name"] as? String)?.takeIf(String::isNotBlank)
            ?: throw IllegalStateException("O perfil existente não tem um nome válido.")
        email = profile.email.takeIf(String::isNotBlank)
            ?: (existing["email"] as? String)?.takeIf(String::isNotBlank)
            ?: throw IllegalStateException("O perfil existente não tem um e-mail válido.")
        createdAt = existing["createdAt"]
            ?: throw IllegalStateException("O perfil existente não tem uma data de criação válida.")
        require(uid == profile.uid) { "O UID do perfil existente não corresponde à conta autenticada." }
    }

    return mapOf(
        "uid" to uid,
        "name" to name,
        "email" to email,
        "createdAt" to createdAt,
        "updatedAt" to com.google.firebase.firestore.FieldValue.serverTimestamp()
    )
}
