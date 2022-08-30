package com.gst.synccalender.utils.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


/**
 * Created by gideon on 8/12/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
interface BaseNetworkModel {
    val status: String?
    val message: String?
}

@Parcelize
@JsonClass(generateAdapter = true)
data class SimpleNetworkModel(
    @Json(name = "status")
    override val status: String? = null,
    @Json(name = "error")
    override val message: String? = null
) : BaseNetworkModel, Parcelable