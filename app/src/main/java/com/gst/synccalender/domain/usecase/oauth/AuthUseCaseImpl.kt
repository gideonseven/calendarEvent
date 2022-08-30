package com.gst.synccalender.domain.usecase.oauth

import com.gst.synccalender.domain.model.TokenWrapper
import com.gst.synccalender.domain.repository.IAuthRepository
import com.gst.synccalender.utils.network.RequestType
import com.gst.synccalender.utils.network.succeedMapper
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject


/**
 * Created by gideon on 8/30/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */

@BindType(installIn = BindType.Component.VIEW_MODEL)
class AuthUseCaseImpl @Inject constructor(
    private val repository: IAuthRepository
) : IAuthUseCase {
    override fun submitCodeForGettingToken(
        requestType: RequestType,
        code: String,
        clientId: String,
        redirectUri: String,
        grantType: String
    ) = repository.submitCodeForGettingToken(requestType, code, clientId, redirectUri, grantType)
        .succeedMapper { response ->
            response?.let {
                TokenWrapper(
                    accessToken = it.accessToken,
                    tokenType = it.tokenType,
                    expiresIn = it.expiresIn,
                    refreshToken = it.refreshToken
                )
            }
        }
}