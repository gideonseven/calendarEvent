package com.gst.synccalender.usecase

import com.gst.synccalender.utils.network.RequestType
import com.gst.synccalender.utils.network.ResponseState
import com.paulrybitskyi.hiltbinder.BindType
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody


/**
 * Created by gideon on 8/28/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@BindType(installIn = BindType.Component.VIEW_MODEL)
class CalendarUseCaseImpl : ICalendarUseCase{
    override fun getGalleries(
        requestType: RequestType,
        page: Int,
        perPage: Int,
        orderBy: String
    ): Flow<ResponseState<RequestType, ResponseBody>> {
        TODO("Not yet implemented")
    }
}