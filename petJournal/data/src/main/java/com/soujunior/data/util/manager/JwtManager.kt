package com.soujunior.data.util.manager

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class JwtManager(context: Context) {

    private val mainKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        sharedPrefsFile,
        mainKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun getToken(): String? {
        return sharedPreferences.getString(JWT_KEY, null)
    }

    fun setToken(jwtToken: String) {
        with(sharedPreferences.edit()) {
            putString(JWT_KEY, jwtToken)
            apply()
        }
    }

    companion object {
        const val JWT_KEY: String = "JWT_PREFERENCE"
        const val sharedPrefsFile: String = "jwt_file"
    }
}