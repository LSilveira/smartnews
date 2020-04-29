package com.lsilveira.smartnews.converter

import com.lsilveira.smartnews.form.DateType
import org.springframework.core.convert.converter.Converter
import java.text.SimpleDateFormat

class DateTypeToStringConverter : Converter<DateType, String>
{
    override fun convert(date: DateType): String?
    {
        val format = SimpleDateFormat("dd/MM/yyyy")
        return format.format(date.date)
    }
}