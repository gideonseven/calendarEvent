package com.gst.synccalender.features.auth

import com.gst.synccalender.domain.model.TokenWrapper
import com.gst.synccalender.utils.UiEffect
import com.gst.synccalender.utils.UiEvent
import com.gst.synccalender.utils.UiState
import com.gst.synccalender.utils.network.RequestType
import com.gst.synccalender.utils.network.ResponseState


/**
 * Created by gideon on 8/30/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
class AuthContract {

    sealed class AuthEffect : UiEffect {
        object NavigateToCalendarFragment : AuthEffect()
        class ShowToast(val message: String) : AuthEffect()
    }

    sealed class AuthEvent : UiEvent {
        object SubmitCode : AuthEvent()
    }

    data class AuthState(
        val responseStateSubmitCode: ResponseState<RequestType, TokenWrapper?> = ResponseState.Empty,
    ) : UiState
}