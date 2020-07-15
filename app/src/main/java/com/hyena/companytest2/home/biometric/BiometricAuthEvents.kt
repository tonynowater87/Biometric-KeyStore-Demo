package com.hyena.companytest2.home.biometric

import java.lang.Exception

sealed class BiometricAuthEvents {
    object Authenticating: BiometricAuthEvents()
    object AuthenticationSucceeded: BiometricAuthEvents()
    object AuthenticationFailed: BiometricAuthEvents()
    object UnSupportedBiometricAuth: BiometricAuthEvents()
    data class AuthenticationError(val errorCode: Int, val errorMessage: CharSequence): BiometricAuthEvents()
    data class EncryptCrypto(val encrypted: String): BiometricAuthEvents()
    data class DecryptCrypto(val decrypted: String): BiometricAuthEvents()
    data class CryptographyError(val exception: Exception): BiometricAuthEvents()
}