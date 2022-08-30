package com.gst.synccalender.features.calendar

import androidx.databinding.Bindable
import com.gst.synccalender.data.remote.dto.RequestCalendar
import com.gst.synccalender.domain.model.End
import com.gst.synccalender.domain.model.Start
import com.gst.synccalender.domain.usecase.calendar.ICalendarUseCase
import com.gst.synccalender.features.calendar.CalendarContract.*
import com.gst.synccalender.utils.AppViewModel
import com.gst.synccalender.utils.Constants
import com.gst.synccalender.utils.network.*
import com.skydoves.bindables.bindingProperty
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by gideon on 8/28/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val calendarUseCase: ICalendarUseCase
) : AppViewModel<CalendarEvent, CalendarState, CalendarEffect>() {


    @get: Bindable
    var token: String by bindingProperty(Constants.TEXT_BLANK)


    override fun createInitialState() = CalendarState()

    override fun handleEvent(event: CalendarEvent) {
        when (event) {
            is CalendarEvent.SubmitCalendarEvent -> {
                if (token != Constants.TEXT_BLANK) {
                    submitEvent()
                }
            }
        }
    }

    private fun submitEvent() {
        val requestBody = RequestCalendar(
            summary = "JUDUL DARI EVENT",
            start = Start("2022-08-31T11:00:00+00:00"),
            end = End("2022-09-01T11:00:00+00:00"),
            description = "DESKRIPSI DARI EVENT NYA APA NIH"
        )
        launchRequest {
            setState {
                copy(responseSubmitEvent = ResponseState.Loading(RequestType.SUBMIT_EVENT))
            }
            calendarUseCase.submitEvent(
                requestType = RequestType.SUBMIT_EVENT,
                token = "Bearer $token",
                requestBody = requestBody
            ).handleErrors(
                RequestType.SUBMIT_EVENT
            ).handleResult(
                updateState = {
                    setState { copy(responseSubmitEvent = it) }
                },
                onSuccess = { _, wrapper ->
                    Timber.e("**** ${wrapper?.responseString}")
                },
                onNotAuthorized = {
                    // DO NOTHING
                }
            )
        }
    }
}