package com.gst.synccalender.domain.repository

import com.gst.synccalender.data.remote.dto.RequestCalendar
import com.gst.synccalender.utils.network.Repository
import com.gst.synccalender.utils.network.RequestType
import com.gst.synccalender.utils.network.ResponseState
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody


/**
 * Created by gideon on 8/30/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
interface ICalendarRepository : Repository {
    fun submitEvent(
        requestType: RequestType,
        token: String,
        calendar: RequestCalendar
    ): Flow<ResponseState<RequestType, ResponseBody?>>
}