package com.soujunior.data.util.manager

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class JwtManager private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences = try {
        createEncryptedSharedPreferences(context)
    } catch (e: Exception) {
        context.deleteSharedPreferences(sharedPrefsFile)
        createEncryptedSharedPreferences(context)
    }

    private fun createEncryptedSharedPreferences(context: Context): SharedPreferences {
        val mainKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            sharedPrefsFile,
            mainKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun getToken(): String? {
        return sharedPreferences.getString(JWT_KEY, null)
    }

    fun deleteToken(): Boolean {
        return try {
            with(sharedPreferences.edit()) {
                putString(JWT_KEY, "")
                apply()
            }
            true
        } catch (e: Exception) {
            false
        }
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

        @Volatile
        private var INSTANCE: JwtManager? = null

        fun getInstance(context: Context): JwtManager =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: JwtManager(context).also { INSTANCE = it }
            }
    }
}
