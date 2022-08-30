package com.gst.synccalender.features.calendar

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
class CalendarContract {

    sealed class CalendarEffect : UiEffect {

    }

    sealed class CalendarEvent : UiEvent {

    }

    data class CalendarState(
        val responseStateGalleries: ResponseState<RequestType, List<Any>?> = ResponseState.Empty,
    ) : UiState
}