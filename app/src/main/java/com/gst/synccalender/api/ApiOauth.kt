package com.gst.synccalender.api

import com.skydoves.sandwich.ApiResponse
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.GET


/**
 * Created by gideon on 8/28/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
interface ApiOauth {

    @GET
    suspend fun getOauthAuthorizationCode(
        @Field("code") code: String?,
        @Field("client_id") client_id: String?,
        @Field("redirect_uri") redirect_uri: String?,
        @Field("grant_type") grant_type: String?
    ): ApiResponse<ResponseBody>
}