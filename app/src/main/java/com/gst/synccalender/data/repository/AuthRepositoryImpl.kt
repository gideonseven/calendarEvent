package com.gst.synccalender.data.repository

import com.gst.synccalender.data.remote.api.ApiOauth
import com.gst.synccalender.domain.repository.IAuthRepository
import com.gst.synccalender.utils.network.RequestType
import com.gst.synccalender.utils.network.getResult
import com.gst.synccalender.utils.network.succeedMapper
import com.paulrybitskyi.hiltbinder.BindType
import com.squareup.moshi.Moshi
import javax.inject.Inject


/**
 * Created by gideon on 8/30/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */

@BindType(installIn = BindType.Component.VIEW_MODEL)
class AuthRepositoryImpl @Inject constructor(
    private val apiOauth: ApiOauth,
    private val moshi: Moshi
) : IAuthRepository {
    override fun submitCodeForGettingToken(
        requestType: RequestType,
        code: String,
        clientId: String,
        redirectUri: String,
        grantType: String
    ) = getResult(
        requestType,
        call = { apiOauth.submitCodeForGettingToken(code, clientId, redirectUri, grantType) },
        converter = {
            it
        }).succeedMapper {
        it?.second
    }
}