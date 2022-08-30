package com.gst.synccalender.domain.usecase.calendar

import com.gst.synccalender.data.remote.dto.RequestCalendar
import com.gst.synccalender.domain.model.CalendarWrapper
import com.gst.synccalender.domain.repository.ICalendarRepository
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
class CalendarUseCaseImpl @Inject constructor(
    private val repository: ICalendarRepository
) : ICalendarUseCase {
    override fun submitEvent(
        requestType: RequestType,
        token: String,
        requestBody: RequestCalendar
    ) = repository.submitEvent(requestType, token, requestBody)
        .succeedMapper { response ->
            response?.let {
                CalendarWrapper(responseString = it.string())
            }
        }
}