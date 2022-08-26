package com.gst.synccalender

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by gideon on 8/26/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
object CalendarHelper {

//    https://ideone.com/DW4vDg
    @Synchronized
    @Throws(ParseException::class, IndexOutOfBoundsException::class)
    fun parseRFC3339Date(dateString: String): Date {
        var mDateString = dateString
        var d: Date

        //if there is no time zone, we don't need to do any special parsing.
        if (mDateString.endsWith("Z")) {
            try {
                val s = SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss'Z'",
                    Locale.US
                ) //spec for RFC3339 with a 'Z'
                s.timeZone = TimeZone.getTimeZone("UTC")
                d = s.parse(mDateString) as Date
            } catch (pe: ParseException) { //try again with optional decimals
                val s = SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'",
                    Locale.US
                ) //spec for RFC3339 with a 'Z' and fractional seconds
                s.timeZone = TimeZone.getTimeZone("UTC")
                s.isLenient = true
                d = s.parse(mDateString) as Date
            }
            return d
        }

        //step one, split off the timezone.
        val firstPart: String
        var secondPart: String
        if (mDateString.lastIndexOf('+') == -1) {
            firstPart = mDateString.substring(0, mDateString.lastIndexOf('-'))
            secondPart = mDateString.substring(mDateString.lastIndexOf('-'))
        } else {
            firstPart = mDateString.substring(0, mDateString.lastIndexOf('+'))
            secondPart = mDateString.substring(mDateString.lastIndexOf('+'))
        }

        //step two, remove the colon from the timezone offset
        secondPart = secondPart.substring(
            0,
            secondPart.indexOf(':')
        ) + secondPart.substring(secondPart.indexOf(':') + 1)
        mDateString = firstPart + secondPart
        var s = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()) //spec for RFC3339
        try {
            d = s.parse(mDateString) as Date
        } catch (pe: ParseException) { //try again with optional decimals
            s = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ",
                Locale.getDefault()
            ) //spec for RFC3339 (with fractional seconds)
            s.isLenient = true
            d = s.parse(mDateString) as Date
        }
        return d
    }


    fun parseRFC3339Calendar(dateString: String): Calendar {
        val calendar = Calendar.getInstance()
        try {
            calendar.time = parseRFC3339Date(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return calendar
    }

    fun countDueDate(stringDate: String): Long {
        val calInput: Calendar = parseRFC3339Calendar(stringDate)
        val calNow: Calendar = convertToCalendar(
            Calendar.getInstance()[Calendar.DAY_OF_MONTH],
            Calendar.getInstance()[Calendar.MONTH] + 1,
            Calendar.getInstance()[Calendar.YEAR]
        )
        return (calInput.time.time - calNow.time.time) / (24 * 60 * 60 * 1000)
    }

    fun convertToCalendar(day: Int, month: Int, year: Int): Calendar {
        val pattern = "yyyy/MM/dd"
        val data = "$year/$month/$day"
        val calendar = Calendar.getInstance()
        try {
            val df = SimpleDateFormat(pattern, Locale.US)
            df.timeZone = TimeZone.getTimeZone("UTC")
            calendar.time = df.parse(data) as Date
            calendar[Calendar.HOUR] = 0
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            return calendar
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return calendar
    }
}