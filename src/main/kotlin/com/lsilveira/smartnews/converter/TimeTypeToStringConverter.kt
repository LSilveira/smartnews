package com.lsilveira.smartnews.converter

import com.lsilveira.smartnews.form.TimeType
import org.springframework.core.convert.converter.Converter
import java.text.SimpleDateFormat

class TimeTypeToStringConverter : Converter<TimeType, String>
{
    override fun convert(date: TimeType): String?
    {
        val format = SimpleDateFormat("HH:mm:ss")
        return format.format(date.time)
    }
}