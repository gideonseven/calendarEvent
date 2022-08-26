package com.gst.synccalender.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET


/**
 * Created by gideon on 8/26/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
interface CalendarApi {
    @GET("users/me/calendarList")
    fun getCalendarList(): Call<JsonObject>
}