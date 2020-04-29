package com.lsilveira.smartnews.util

import org.apache.commons.validator.routines.UrlValidator

object UrlHelper
{
    fun validateUrl(url: String) : Boolean
    {
        val urlValidator = UrlValidator()

        return urlValidator.isValid(url)
    }
}