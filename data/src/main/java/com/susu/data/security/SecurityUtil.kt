package com.susu.data.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.BLOCK_MODE_GCM
import android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE
import android.security.keystore.KeyProperties.KEY_ALGORITHM_AES
import android.security.keystore.KeyProperties.PURPOSE_DECRYPT
import android.security.keystore.KeyProperties.PURPOSE_ENCRYPT
import java.security.Key
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec

object SecurityUtil {

    private const val KEYSTORE_NAME = "AndroidKeyStore"
    private const val CIPHER_OPTION = "AES/GCM/NOPadding"
    private const val SECRET_KEY = "SecretKey"
    private const val CHARSET = "UTF-8"
    private const val GCM_PARAM_LEN = 128

    private val cipher = Cipher.getInstance(CIPHER_OPTION)
    private val keyStore = KeyStore.getInstance(KEYSTORE_NAME).apply { load(null) }
    private val keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM_AES, KEYSTORE_NAME)
    private val charset = charset(CHARSET)

    fun encrypt(data: String): ByteArray {
        return cipher.apply {
            init(Cipher.ENCRYPT_MODE, getSecretKey())
        }.doFinal(data.toByteArray(charset))
    }

    fun decrypt(encryptedData: ByteArray): String {
        return cipher.apply {
            init(Cipher.DECRYPT_MODE, getSecretKey(), GCMParameterSpec(GCM_PARAM_LEN, cipher.iv))
        }.doFinal(encryptedData).toString(charset)
    }

    private fun getSecretKey(): Key {
        return if (keyStore.isKeyEntry(SECRET_KEY)) {
            keyStore.getKey(SECRET_KEY, null)
        } else {
            keyGenerator.apply {
                init(
                    KeyGenParameterSpec
                        .Builder(SECRET_KEY, PURPOSE_ENCRYPT or PURPOSE_DECRYPT)
                        .setBlockModes(BLOCK_MODE_GCM)
                        .setEncryptionPaddings(ENCRYPTION_PADDING_NONE)
                        .build(),
                )
            }.generateKey()
        }
    }
}
