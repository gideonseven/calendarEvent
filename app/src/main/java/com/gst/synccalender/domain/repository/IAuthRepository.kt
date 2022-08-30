package com.gst.synccalender.domain.repository

import com.gst.synccalender.data.remote.dto.TokenResponse
import com.gst.synccalender.utils.network.Repository
import com.gst.synccalender.utils.network.RequestType
import com.gst.synccalender.utils.network.ResponseState
import kotlinx.coroutines.flow.Flow


/**
 * Created by gideon on 8/30/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
interface IAuthRepository:Repository {
    fun submitCodeForGettingToken(
        requestType: RequestType,
        code: String,
        clientId: String,
        redirectUri: String,
        grantType: String
    ): Flow<ResponseState<RequestType, TokenResponse?>>
}