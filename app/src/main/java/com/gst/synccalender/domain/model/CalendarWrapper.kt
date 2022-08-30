package com.gst.synccalender.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * Created by gideon on 8/30/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@Parcelize
data class CalendarWrapper(
    val responseString: String? = null
) : Parcelable