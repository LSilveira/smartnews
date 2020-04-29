package com.lsilveira.smartnews.converter

import com.lsilveira.smartnews.form.DateType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.convert.converter.Converter
import java.text.ParseException
import java.text.SimpleDateFormat

class StringToDateTypeConverter : Converter<String, DateType>
{
    private val logger: Logger = LoggerFactory.getLogger(StringToDateTypeConverter::class.java)

    override fun convert(string: String): DateType?
    {
        if (string.isBlank()) {
            return null
        }

        val formatter = SimpleDateFormat("dd/MM/yyyy")

        return try {
            val date = formatter.parse(string)
            DateType(date)
        } catch (e: ParseException) {
            logger.warn(e.message)
            null
        }
    }
}