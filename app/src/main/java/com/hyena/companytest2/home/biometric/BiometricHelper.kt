package com.hyena.companytest2.home.biometric

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.hyena.companytest2.R
import com.hyena.companytest2.home.biometric.keystore.KeyStoreHelper
import io.reactivex.rxjava3.subjects.PublishSubject


class BiometricHelper(
    private val context: Context,
    private val biometricManager: BiometricManager,
    private val keyStoreHelper: KeyStoreHelper
) {

    enum class Purpose {
        Encrypt, Decrypt, Normal
    }

    val events: PublishSubject<BiometricAuthEvents> = PublishSubject.create()

    fun authenticationWithCrypto(activity: FragmentActivity, input: String, purpose: Purpose) {

        events.onNext(BiometricAuthEvents.Authenticating)

        if (!isSupported()) {
            events.onNext(BiometricAuthEvents.UnSupportedBiometricAuth)
            return
        }

        try {
            val cryptoObj = when (purpose) {
                Purpose.Encrypt -> BiometricPrompt.CryptoObject(keyStoreHelper.getCipherForEncrypt())
                Purpose.Decrypt -> BiometricPrompt.CryptoObject(keyStoreHelper.getCipherForDecrypt())
                else -> null
            }

            val prompt = BiometricPrompt(
                activity,
                getExecutor(),
                object : BiometricPrompt.AuthenticationCallback() {

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)

                        result.cryptoObject?.cipher?.also { cipher ->
                            when (purpose) {
                                Purpose.Encrypt -> {
                                    val encrypted = keyStoreHelper.encrypt(cipher, input)
                                    events.onNext(BiometricAuthEvents.EncryptCrypto(encrypted))
                                }
                                Purpose.Decrypt -> {
                                    val decrypted = keyStoreHelper.decrypt(cipher, input)
                                    events.onNext(BiometricAuthEvents.DecryptCrypto(decrypted))
                                }
                                Purpose.Normal -> {
                                    events.onNext(BiometricAuthEvents.AuthenticationSucceeded)
                                }
                            }
                        }
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        events.onNext(BiometricAuthEvents.AuthenticationFailed)
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        events.onNext(BiometricAuthEvents.AuthenticationError(errorCode, errString))
                    }

                })

            if (purpose == Purpose.Normal) {
                prompt.authenticate(getPromptInfo(purpose = purpose))
            } else {
                prompt.authenticate(getPromptInfo(purpose = purpose), cryptoObj!!)
            }
        } catch (exception: Exception) {
            events.onNext(BiometricAuthEvents.CryptographyError(exception))
        }
    }

    private fun getExecutor() = ContextCompat.getMainExecutor(context)

    private fun getPromptInfo(purpose: Purpose): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setConfirmationRequired(true)
            .setDeviceCredentialAllowed(purpose == Purpose.Normal)
            .setTitle(context.getString(R.string.promptTitle))
            .setDescription(context.getString(R.string.promptDescription))
            .apply { if (purpose != Purpose.Normal) setNegativeButtonText(context.getString(android.R.string.cancel)) }
            .build()
    }

    private fun isSupported(): Boolean {
        return biometricManager.canAuthenticate() == BIOMETRIC_SUCCESS
    }
}