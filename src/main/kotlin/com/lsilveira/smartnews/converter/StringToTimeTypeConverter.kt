package com.lsilveira.smartnews.converter

import com.lsilveira.smartnews.form.TimeType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.convert.converter.Converter
import java.text.ParseException
import java.text.SimpleDateFormat

class StringToTimeTypeConverter : Converter<String, TimeType>
{
    private val logger: Logger = LoggerFactory.getLogger(StringToTimeTypeConverter::class.java)

    override fun convert(string: String): TimeType?
    {
        if (string.isBlank()) {
            return null
        }

        val formatter = SimpleDateFormat("HH:mm:ss")

        return try {
            val time = formatter.parse(string)
            TimeType(time)
        } catch (e: ParseException) {
            logger.warn(e.message)
            null
        }
    }
}