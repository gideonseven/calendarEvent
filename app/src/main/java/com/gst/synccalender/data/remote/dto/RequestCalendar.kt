package com.gst.synccalender.data.remote.dto

import com.gst.synccalender.domain.model.End
import com.gst.synccalender.domain.model.Start
import com.squareup.moshi.Json


/**
 * Created by gideon on 8/30/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
data class RequestCalendar(
    @Json(name = "summary")
    val summary: String,
    @Json(name = "start")
    val start: Start,
    @Json(name = "description")
    val description: String,
    @Json(name = "end")
    val end: End
)
