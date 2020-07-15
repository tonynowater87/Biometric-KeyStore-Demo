package com.hyena.companytest2.home.biometric.keystore

import android.content.Context
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import timber.log.Timber
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.util.Calendar
import javax.crypto.Cipher
import javax.security.auth.x500.X500Principal

class KeyStoreHelper(private val context: Context) {

    companion object {
        private const val AndroidKeyStore = "AndroidKeyStore"
        private const val HyenaTestKeyAlias = "HyenaTestRsaKeyAlias"
        private const val RSAMode = "${KeyProperties.KEY_ALGORITHM_RSA}/${KeyProperties.BLOCK_MODE_ECB}/${KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1}"
        private const val KeySize = 512 // RSA key size must be >= 512 and <= 8192
        const val MaxPlainTextLengthForASCII = KeySize / 8 - 11
        const val MaxPlainTextLengthForUTF8 = MaxPlainTextLengthForASCII / 3
    }

    private var keyStore: KeyStore = KeyStore.getInstance(AndroidKeyStore).apply {
        load(null)
    }

    fun getCipherForEncrypt(): Cipher {
        init()
        val rsaKey = keyStore.getEntry(HyenaTestKeyAlias, null) as KeyStore.PrivateKeyEntry
        return getCipher().apply {
            init(Cipher.ENCRYPT_MODE, rsaKey.certificate.publicKey)
        }
    }

    fun getCipherForDecrypt(): Cipher {
        init()
        val rsaKey = keyStore.getEntry(HyenaTestKeyAlias, null) as KeyStore.PrivateKeyEntry
        return getCipher().apply {
            init(Cipher.DECRYPT_MODE, rsaKey.privateKey)
        }
    }

    fun encrypt(cipher: Cipher, plainText: String): String {
        Timber.d("[DEBUG] [encrypt] input => $plainText")
        val encrypted = cipher.doFinal(plainText.toByteArray())
        return Base64.encodeToString(encrypted, Base64.DEFAULT).also { Timber.d("[DEBUG] [encrypt] output => $it") }
    }

    fun decrypt(cipher: Cipher, encryptedBase64: String): String {
        Timber.d("[DEBUG] [decrypt] input => $encryptedBase64")
        val encryptedByte = Base64.decode(encryptedBase64, Base64.DEFAULT)
        val decrypted = cipher.doFinal(encryptedByte)
        return String(decrypted).also { Timber.d("[DEBUG] [decrypt] output => $it") }
    }

    private fun init() {
        if (!keyStore.containsAlias(HyenaTestKeyAlias)) {
            Timber.d("[DEBUG] [KEY] $HyenaTestKeyAlias doesn't exist")
            generateKeys()
        } else {
            Timber.d("[DEBUG] [KEY] exist => ${keyStore.provider}, ${keyStore.aliases().toList()}")
        }
    }

    private fun generateKeys() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            generateRSAKeyAboveApi23()
        } else {
            generateRSAKeyBelowApi23(context)
        }
    }

    private fun generateRSAKeyAboveApi23() {

        val keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, AndroidKeyStore)

        val spec = KeyGenParameterSpec
            .Builder(HyenaTestKeyAlias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
            .setKeySize(KeySize)
            .setUserAuthenticationRequired(true)
            .build()

        keyPairGenerator.initialize(spec)
        keyPairGenerator.generateKeyPair()
    }

    private fun generateRSAKeyBelowApi23(context: Context) {

        val keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, AndroidKeyStore)

        val start = Calendar.getInstance()
        val end = Calendar.getInstance().apply { add(Calendar.YEAR, 100) }

        val spec = KeyPairGeneratorSpec.Builder(context)
            .setAlias(HyenaTestKeyAlias)
            .setSubject(X500Principal("CN=$HyenaTestKeyAlias"))
            .setSerialNumber(BigInteger.ONE)
            .setStartDate(start.time)
            .setEndDate(end.time)
            .setKeySize(KeySize)
            .build()

        keyPairGenerator.initialize(spec)
        keyPairGenerator.generateKeyPair()
    }

    private fun getCipher(): Cipher {
        return try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) { // below android m
                Cipher.getInstance(
                    RSAMode,
                    "AndroidOpenSSL"
                ) // error in android 6: InvalidKeyException: Need RSA private or public key
            } else { // android m and above
                Cipher.getInstance(
                    RSAMode,
                    "AndroidKeyStoreBCWorkaround"
                ) // error in android 5: NoSuchProviderException: Provider not available: AndroidKeyStoreBCWorkaround
            }
        } catch (exception: Exception) {
            throw RuntimeException(
                "getCipher: Failed to get an instance of Cipher",
                exception
            )
        }
    }
}