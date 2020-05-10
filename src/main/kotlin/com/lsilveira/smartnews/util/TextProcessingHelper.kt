package com.lsilveira.smartnews.util

import org.jsoup.Jsoup

object TextProcessingHelper
{
    fun cleanHtml(html: String): String?
    {
        if (html.isBlank())
            return null

        return Jsoup.parse(html).text()
    }
}