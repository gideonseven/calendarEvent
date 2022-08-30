package com.gst.synccalender.utils.network

/**
 * Created by gideon on 8/12/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
sealed class RequestType : CoreRequestType() {
    object SUBMIT_CODE : RequestType()
    object GET_SEARCH_PHOTOS : RequestType()
}