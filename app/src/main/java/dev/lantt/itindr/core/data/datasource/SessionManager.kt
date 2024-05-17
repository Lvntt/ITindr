package dev.lantt.itindr.core.data.datasource

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dev.lantt.itindr.core.data.model.TokenResponse
import dev.lantt.itindr.core.data.model.TokenType

class SessionManager(context: Context) {

    private companion object {
        const val TOKEN_PREFERENCES_KEY = "token_preferences"
        const val ACCESS_TOKEN = "access_token"
        const val ACCESS_TOKEN_EXPIRED_AT = "access_token_expired_at"
        const val REFRESH_TOKEN = "refresh_token"
        const val REFRESH_TOKEN_EXPIRED_AT = "refresh_token_expired_at"
    }

    private val masterKeyAlias = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        TOKEN_PREFERENCES_KEY,
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun fetchToken(tokenType: TokenType): String? {
        return when (tokenType) {
            TokenType.ACCESS -> sharedPreferences.getString(ACCESS_TOKEN, null)
            TokenType.REFRESH -> sharedPreferences.getString(REFRESH_TOKEN, null)
        }
    }

    fun saveTokens(tokenResponse: TokenResponse) {
        sharedPreferences.edit()
            .putString(ACCESS_TOKEN, tokenResponse.accessToken)
            .putLong(ACCESS_TOKEN_EXPIRED_AT, tokenResponse.accessTokenExpiredAt)
            .putString(REFRESH_TOKEN, tokenResponse.refreshToken)
            .putLong(REFRESH_TOKEN_EXPIRED_AT, tokenResponse.refreshTokenExpiredAt)
            .apply()
    }

    fun isTokenExpired(tokenType: TokenType): Boolean {
        return when (tokenType) {
            TokenType.ACCESS -> sharedPreferences.getLong(ACCESS_TOKEN_EXPIRED_AT, 0L) < System.currentTimeMillis()
            TokenType.REFRESH -> sharedPreferences.getLong(REFRESH_TOKEN_EXPIRED_AT, 0L) < System.currentTimeMillis()
        }
    }

}