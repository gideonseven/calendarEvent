package com.gst.synccalender.repository

import com.gst.synccalender.utils.network.Repository
import com.gst.synccalender.utils.network.RequestType
import com.gst.synccalender.utils.network.ResponseState
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody


/**
 * Created by gideon on 8/28/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
interface ICalendarRepository : Repository{

    fun getSearchPhotos(
        requestType: RequestType,
        query: String,
        page: Int,
        perPage: Int
    ): Flow<ResponseState<RequestType, ResponseBody?>>
}