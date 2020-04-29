package com.lsilveira.smartnews.util

import java.util.*


object DateHelper
{
    fun combineDateTime(date: Date, time: Date) : Date?
    {
        val dateCal = GregorianCalendar()
        val timeCal = GregorianCalendar()

        dateCal.time = date
        timeCal.time = time

        val year = dateCal[Calendar.YEAR]
        val month = dateCal[Calendar.MONTH]
        val day = dateCal[Calendar.DAY_OF_MONTH]

        val hour = timeCal[Calendar.HOUR_OF_DAY]
        val minute = timeCal[Calendar.MINUTE]
        val second = timeCal[Calendar.SECOND]

        val dateTimeCal = GregorianCalendar(year, month, day, hour, minute, second)

        return dateTimeCal.time
    }
}