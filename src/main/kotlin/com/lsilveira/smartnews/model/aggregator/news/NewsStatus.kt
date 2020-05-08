package com.lsilveira.smartnews.model.aggregator.news

enum class NewsStatus(val description: String)
{
    RAW("Raw"),
    PROCESSED("Processed"),
    ANALYSED("Analysed")
}