package com.wakeme.app.ui.auth

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AuthFormValidationTest {
    @Test fun rejectsBlankRegistrationName() {
        assertEquals("Informe seu nome.", AuthFormValidation.validate(" ", "ana@example.com", "secret1", true))
    }

    @Test fun rejectsInvalidEmail() {
        assertEquals("Informe um e-mail válido.", AuthFormValidation.validate("Ana", "invalid", "secret1", true))
    }

    @Test fun rejectsShortRegistrationPassword() {
        assertEquals("A senha precisa ter pelo menos 6 caracteres.", AuthFormValidation.validate("Ana", "ana@example.com", "123", true))
    }

    @Test fun acceptsValidRegistrationAndLogin() {
        assertNull(AuthFormValidation.validate("Ana", "ana@example.com", "secret1", true))
        assertNull(AuthFormValidation.validate("", "ana@example.com", "x", false))
    }
}
