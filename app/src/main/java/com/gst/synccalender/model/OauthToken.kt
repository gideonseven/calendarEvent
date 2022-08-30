package com.gst.synccalender.model

import android.content.Context
import com.gst.synccalender.CalenderApp
import com.squareup.moshi.Json


/**
 * Created by gideon on 8/30/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
data class OauthToken(
    @Json(name = "access_token")
    val accessToken: String? = null,

    @Json(name = "token_type")
    val tokenType: String? = null,

    @Json(name = "expires_in")
    val expiresIn: Long = 0,

    @Json(name = "refresh_token")
    val refreshToken: String? = null,

    private var expiredAfterMilli: Long = 0

) {

    fun save() {
        expiredAfterMilli = System.currentTimeMillis() + expiresIn * 1000

        val sp =
            CalenderApp().getSharedPreferences(OAUTH_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)

        val ed = sp.edit()
        ed.putString(SP_TOKEN_KEY, accessToken)
        ed.putString(SP_TOKEN_TYPE_KEY, tokenType)
        ed.putLong(SP_TOKEN_EXPIRED_AFTER_KEY, expiredAfterMilli)
        ed.putString(SP_REFRESH_TOKEN_KEY, refreshToken)
        ed.apply()
    }

    companion object {
        /***********************************************************
         * Constants
         */
        private const val OAUTH_SHARED_PREFERENCE_NAME = "OAuthPrefs"
        private const val SP_TOKEN_KEY = "token"
        private const val SP_TOKEN_TYPE_KEY = "token_type"
        private const val SP_TOKEN_EXPIRED_AFTER_KEY = "expired_after"
        private const val SP_REFRESH_TOKEN_KEY = "refresh_token"
    }
}
