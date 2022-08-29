package com.gst.synccalender.data.repository

import com.gst.synccalender.api.ApiCalendar
import com.gst.synccalender.api.ApiOauth
import com.gst.synccalender.repository.ICalendarRepository
import com.gst.synccalender.utils.network.RequestType
import com.gst.synccalender.utils.network.ResponseState
import com.paulrybitskyi.hiltbinder.BindType
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import javax.inject.Inject


/**
 * Created by gideon on 8/29/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@BindType(installIn = BindType.Component.VIEW_MODEL)
class CalendarRepositoryImpl @Inject constructor(
    private val apiCalendar: ApiCalendar,
    private val apiOauth: ApiOauth
) : ICalendarRepository {
    override fun getSearchPhotos(
        requestType: RequestType,
        query: String,
        page: Int,
        perPage: Int
    ): Flow<ResponseState<RequestType, ResponseBody?>> {
        TODO("Not yet implemented")
    }
}