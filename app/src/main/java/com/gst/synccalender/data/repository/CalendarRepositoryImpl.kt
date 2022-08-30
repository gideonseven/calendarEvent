package com.gst.synccalender.data.repository

import com.gst.synccalender.data.remote.api.ApiCalendar
import com.gst.synccalender.data.remote.dto.RequestCalendar
import com.gst.synccalender.domain.repository.ICalendarRepository
import com.gst.synccalender.utils.network.RequestType
import com.gst.synccalender.utils.network.getResult
import com.gst.synccalender.utils.network.succeedMapper
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject


/**
 * Created by gideon on 8/30/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@BindType(installIn = BindType.Component.VIEW_MODEL)
class CalendarRepositoryImpl @Inject constructor(
    private val apiCalendar: ApiCalendar
) : ICalendarRepository {

    override fun submitEvent(
        requestType: RequestType,
        token: String,
        calendar: RequestCalendar
    ) = getResult(
        requestType,
        call = { apiCalendar.submitEvent(token = token, loginRequest = calendar) },
        converter = {
            it
        }).succeedMapper {
        it?.second
    }
}