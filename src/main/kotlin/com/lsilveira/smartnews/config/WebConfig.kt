package com.lsilveira.smartnews.config

import com.lsilveira.smartnews.converter.DateTypeToStringConverter
import com.lsilveira.smartnews.converter.TimeTypeToStringConverter
import com.lsilveira.smartnews.converter.StringToDateTypeConverter
import com.lsilveira.smartnews.converter.StringToTimeTypeConverter
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer
{
    override fun addFormatters(registry: FormatterRegistry)
    {
        // Date type converter
        registry.addConverter(StringToDateTypeConverter())
        registry.addConverter(DateTypeToStringConverter())

        // Hour type converter
        registry.addConverter(StringToTimeTypeConverter())
        registry.addConverter(TimeTypeToStringConverter())

        super.addFormatters(registry)
    }
}