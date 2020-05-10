package com.lsilveira.smartnews.model.aggregator.news

enum class NewsStatus(val description: String)
{
    RAW("Raw"),
    CLEANED("Cleaned"),
    PROCESSED("Processed"),
    ANALYSED("Analysed")
}