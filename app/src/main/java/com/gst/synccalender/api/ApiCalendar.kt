package com.gst.synccalender.api

import com.skydoves.sandwich.ApiResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


/**
 * Created by gideon on 8/26/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
interface ApiCalendar {
    @GET("calendar/v3/users/me/calendarList")
    suspend fun getCalendarList(): ApiResponse<ResponseBody>

    @POST("calendar/v3/calendars/{calendarId}/events")
    suspend fun submitEvent(@Path("calendarId") calendarId: String = "primary"): ApiResponse<ResponseBody>
}