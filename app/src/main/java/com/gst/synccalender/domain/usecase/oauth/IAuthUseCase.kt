package com.gst.synccalender.domain.usecase.oauth

import com.gst.synccalender.data.remote.dto.TokenResponse
import com.gst.synccalender.domain.model.TokenWrapper
import com.gst.synccalender.utils.network.RequestType
import com.gst.synccalender.utils.network.ResponseState
import kotlinx.coroutines.flow.Flow


/**
 * Created by gideon on 8/30/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
interface IAuthUseCase {
    fun submitCodeForGettingToken(
        requestType: RequestType,
        code: String,
        clientId: String,
        redirectUri: String,
        grantType: String
    ): Flow<ResponseState<RequestType, TokenWrapper?>>
}