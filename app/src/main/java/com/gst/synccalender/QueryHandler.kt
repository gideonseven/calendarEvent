package com.gst.synccalender

import android.content.AsyncQueryHandler
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import java.util.*

/**
 * Created by gideon on 8/26/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
// https://billthefarmer.github.io/blog/adding-a-calendar-event/
class QueryHandler
    (resolver: ContentResolver?) : AsyncQueryHandler(resolver) {

    companion object {
        private const val TAG = "QueryHandler"

        // Projection arrays
        private val CALENDAR_PROJECTION = arrayOf(
            CalendarContract.Calendars._ID
        )

        // The indices for the projection array above.
        private const val CALENDAR_ID_INDEX = 0
        private const val CALENDAR = 0
        private const val EVENT = 1
        private const val REMINDER = 2
        private var queryHandler: QueryHandler? = null


        // insertEvent
        fun insertEvent(
            context: Context, startTime: Long,
            endTime: Long, title: String?
        ) {
            val resolver = context.contentResolver
            if (queryHandler == null) queryHandler = QueryHandler(resolver)

            queryHandler?.startQuery(
                CALENDAR, ContentValues().apply {
                    put(CalendarContract.Events.TITLE, title)
                    put(CalendarContract.Events.DTSTART, startTime)
                    put(CalendarContract.Events.DTEND, endTime)
                    put(CalendarContract.Events.DESCRIPTION, "JANGAN LUPA BAYAR ")
                    put(CalendarContract.Events.CALENDAR_ID, CALENDAR)
                    Log.d(TAG, "Calendar query start")
                }, CalendarContract.Calendars.CONTENT_URI,
                CALENDAR_PROJECTION, null, null, null
            )
        }

        fun deleteEvent(
            context: Context
        ) {
            val resolver = context.contentResolver
            if (queryHandler == null) queryHandler = QueryHandler(resolver)
            queryHandler?.startDelete(
                REMINDER,
                null,
                CalendarContract.Events.CONTENT_URI,
                EVENT.toString(),
                null
            )
        }
    }

    override fun onQueryComplete(token: Int, cookie: Any?, cursor: Cursor) {
        // Use the cursor to move through the returned records
        cursor.moveToFirst()

        // Get the field values
        val calendarID = cursor.getLong(CALENDAR_ID_INDEX)
        Log.d(TAG, "Calendar query complete $calendarID")
        val values = cookie as ContentValues
        values.put(CalendarContract.Events.CALENDAR_ID, calendarID)
        values.put(
            CalendarContract.Events.EVENT_TIMEZONE,
            TimeZone.getDefault().displayName
        )

        startInsert(EVENT, null, CalendarContract.Events.CONTENT_URI, values)
    }

    override fun onInsertComplete(token: Int, cookie: Any?, uri: Uri) {
        Log.d(TAG, "Insert complete " + uri.lastPathSegment)
        if (token == EVENT) {
            val eventID = uri.lastPathSegment?.toLong()
            val values = ContentValues()
            values.put(CalendarContract.Reminders.MINUTES, 10)
            values.put(CalendarContract.Reminders.EVENT_ID, eventID)
            values.put(
                CalendarContract.Reminders.METHOD,
                CalendarContract.Reminders.METHOD_ALERT
            )
            startInsert(REMINDER, null, CalendarContract.Reminders.CONTENT_URI, values)
        }
    }

    override fun onDeleteComplete(token: Int, cookie: Any?, result: Int) {
        Log.d(TAG, "DELETE complete $result")
    }


    // https://stackoverflow.com/a/57214292
    private fun getCalendarId(context: Context) : Long? {
        val projection = arrayOf(CalendarContract.Calendars._ID, CalendarContract.Calendars.CALENDAR_DISPLAY_NAME)

        var calCursor = context.contentResolver.query(
            CalendarContract.Calendars.CONTENT_URI,
            projection,
            CalendarContract.Calendars.VISIBLE + " = 1 AND " + CalendarContract.Calendars.IS_PRIMARY + "=1",
            null,
            CalendarContract.Calendars._ID + " ASC"
        )

        if (calCursor != null && calCursor.count <= 0) {
            calCursor = context.contentResolver.query(
                CalendarContract.Calendars.CONTENT_URI,
                projection,
                CalendarContract.Calendars.VISIBLE + " = 1",
                null,
                CalendarContract.Calendars._ID + " ASC"
            )
        }

        if (calCursor != null) {
            if (calCursor.moveToFirst()) {
                val calName: String
                val calID: String
                val nameCol = calCursor.getColumnIndex(projection[1])
                val idCol = calCursor.getColumnIndex(projection[0])

                calName = calCursor.getString(nameCol)
                calID = calCursor.getString(idCol)

                Log.d(TAG,"Calendar name = $calName Calendar ID = $calID")

                calCursor.close()
                return calID.toLong()
            }
        }
        return null
    }
}