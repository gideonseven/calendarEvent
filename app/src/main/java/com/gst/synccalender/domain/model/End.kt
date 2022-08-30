package com.gst.synccalender.domain.model

import com.squareup.moshi.Json


/**
 * Created by gideon on 8/30/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
data class End(
    @Json(name = "dateTime")
    val dateTime: String
)
