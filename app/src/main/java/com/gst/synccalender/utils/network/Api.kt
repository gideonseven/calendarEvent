package com.gst.synccalender.utils.network


/**
 * Created by gideon on 8/12/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
object Api {
    const val DEVICE = "device"
    const val VERSION = "version"
    const val AUTH_HEADER = "Authorization"
    const val BASE_URL = "https://www.googleapis.com/"
    const val BASE_URL_OAUTH = "https://accounts.google.com/o/oauth2/v2/auth/"
    const val CLIENT_ID = "175437962012-qgcfcnq44a92sn19g6fpf1ha9vacd81p.apps.googleusercontent.com"
    const val REDIRECT_URI = "com.gst.synccalender:/oauth2redirect"
    const val REDIRECT_URI_ROOT = "com.gst.synccalender"
    const val CODE = "code"
    const val ERROR_CODE = "error"
    const val GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code"
    const val GRANT_TYPE_REFRESH_TOKEN = "refresh_token"
    const val API_SCOPE = "https://www.googleapis.com/auth/calendar"

    enum class RequestStatus(val status: String) {
        SUCCESS("success"),
        FAIL("fail"),
        BAD_REQUEST("400"),
        UNAUTHORIZED("401"),
        FORBIDDEN("403"),
        NOT_FOUND("404"),
        SERVER_ERROR("500"),
    }
}