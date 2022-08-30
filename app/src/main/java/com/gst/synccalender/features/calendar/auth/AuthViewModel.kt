package com.gst.synccalender.features.calendar.auth

import androidx.databinding.Bindable
import com.gst.synccalender.domain.usecase.oauth.IAuthUseCase
import com.gst.synccalender.features.calendar.auth.AuthContract.*
import com.gst.synccalender.utils.AppViewModel
import com.gst.synccalender.utils.Constants
import com.gst.synccalender.utils.network.*
import com.gst.synccalender.utils.network.Api.CLIENT_ID
import com.gst.synccalender.utils.network.Api.GRANT_TYPE_AUTHORIZATION_CODE
import com.gst.synccalender.utils.network.Api.REDIRECT_URI
import com.skydoves.bindables.bindingProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by gideon on 8/30/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: IAuthUseCase
) : AppViewModel<AuthEvent, AuthState, AuthEffect>() {


    @get:Bindable
    var mCode: String by bindingProperty(Constants.TEXT_BLANK)

    override fun createInitialState() = AuthState()

    override fun handleEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SubmitCode -> submitCode()
        }
    }

    private fun submitCode() {
        launchRequest {
            setState {
                copy(responseStateSubmitCode = ResponseState.Loading(RequestType.SUBMIT_CODE))
            }
            authUseCase.submitCodeForGettingToken(
                requestType = RequestType.SUBMIT_CODE,
                code = mCode,
                clientId = CLIENT_ID,
                redirectUri = REDIRECT_URI,
                grantType = GRANT_TYPE_AUTHORIZATION_CODE
            ).handleErrors(
                RequestType.SUBMIT_CODE
            ).handleResult(
                updateState = {
                    setState { copy(responseStateSubmitCode = it) }
                },
                onSuccess = { _, auth ->
                    Timber.e("=== $auth")
                    setEffect { AuthEffect.NavigateToCalendarFragment }
                },
                onNotAuthorized = {
                    setEffect { AuthEffect.ShowToast("FORBIDDEN") }
                }
            )
        }
    }
}