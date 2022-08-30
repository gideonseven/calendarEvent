package com.gst.synccalender.features.calendar

import com.gst.synccalender.features.calendar.CalendarContract.*
import com.gst.synccalender.utils.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * Created by gideon on 8/28/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@HiltViewModel
class CalendarViewModel @Inject constructor(

) : AppViewModel<CalendarEvent, CalendarState, CalendarEffect>() {
    override fun createInitialState() = CalendarState()

    override fun handleEvent(event: CalendarEvent) {

    }
}