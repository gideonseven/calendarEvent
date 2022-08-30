package com.gst.synccalender.data.remote.api

import com.gst.synccalender.data.remote.dto.RequestCalendar
import com.gst.synccalender.utils.network.Api
import com.skydoves.sandwich.ApiResponse
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


/**
 * Created by gideon on 8/26/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
interface ApiCalendar {
    @POST("calendar/v3/calendars/{calendarId}/events")
    suspend fun submitEvent(
        @Header(Api.AUTH_HEADER) token: String,
        @Path("calendarId") calendarId: String = "primary",
        @Body loginRequest: RequestCalendar
    ): ApiResponse<ResponseBody>
}