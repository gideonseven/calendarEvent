package com.gst.synccalender.data.remote.dto

import com.gst.synccalender.utils.network.BaseNetworkModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


/**
 * Created by gideon on 8/30/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@JsonClass(generateAdapter = true)
data class TokenResponse(
    @Json(name = "access_token")
    val accessToken: String? = null,
    @Json(name = "token_type")
    val tokenType: String? = null,
    @Json(name = "expires_in")
    val expiresIn: Long = 0,
    @Json(name = "refresh_token")
    val refreshToken: String? = null,
    @Json(name = "status")
    override val status: String?,
    @Json(name = "message")
    override val message: String?
) : BaseNetworkModel