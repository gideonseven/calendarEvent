package com.gst.synccalender.features.calendar

import com.gst.synccalender.domain.model.CalendarWrapper
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
        object ShowToast : CalendarEffect()
    }

    sealed class CalendarEvent : UiEvent {
        object SubmitCalendarEvent : CalendarEvent()
    }

    data class CalendarState(
        val responseSubmitEvent: ResponseState<RequestType, CalendarWrapper?> = ResponseState.Empty
    ) : UiState
}