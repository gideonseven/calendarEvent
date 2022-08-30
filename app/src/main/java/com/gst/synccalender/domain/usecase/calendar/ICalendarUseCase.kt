package com.gst.synccalender.domain.usecase.calendar

import com.gst.synccalender.data.remote.dto.RequestCalendar
import com.gst.synccalender.domain.model.CalendarWrapper
import com.gst.synccalender.utils.network.RequestType
import com.gst.synccalender.utils.network.ResponseState
import kotlinx.coroutines.flow.Flow


/**
 * Created by gideon on 8/30/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
interface ICalendarUseCase {
    fun submitEvent(
        requestType: RequestType,
        token: String,
        requestBody: RequestCalendar
    ): Flow<ResponseState<RequestType, CalendarWrapper?>>
}