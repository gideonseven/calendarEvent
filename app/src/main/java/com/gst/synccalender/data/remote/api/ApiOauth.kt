package com.gst.synccalender.data.remote.api

import com.gst.synccalender.data.remote.dto.TokenResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


/**
 * Created by gideon on 8/28/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
interface ApiOauth {
    /**
     * after we get "code" value from "AuthFragment",
     * we need to submit it, to get Token
     */
    @FormUrlEncoded
    @POST("oauth2/v4/token")
    suspend fun submitCodeForGettingToken(
        @Field("code") code: String?,
        @Field("client_id") client_id: String?,
        @Field("redirect_uri") redirect_uri: String?,
        @Field("grant_type") grant_type: String?
    ): ApiResponse<TokenResponse>
}