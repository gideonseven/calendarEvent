package com.gst.synccalender.feature

import androidx.lifecycle.ViewModel


/**
 * Created by gideon on 8/28/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
class FirstViewModel : ViewModel() {

 /*   fun createServicesCalendar(){
        // Initialize Calendar service with valid OAuth credentials
        val service = Calendar.Builder(httpTransport, jsonFactory, credentials)
            .setApplicationName("com.gst.synccalendar.CalenderApp").build()

        // Iterate through entries in calendar list
        var pageToken: String? = null
        do {
            val calendarList = service.calendarList().list().setPageToken(pageToken).execute()
            val items = calendarList.items
            for (calendarListEntry in items) {
                println(calendarListEntry.summary)
            }
            pageToken = calendarList.nextPageToken
        } while (pageToken != null)
    }*/
}