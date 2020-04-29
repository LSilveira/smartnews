package com.lsilveira.smartnews.service

import com.lsilveira.smartnews.model.aggregator.news.AggregatedData

/**
 * Service to manage news
 */
interface NewsService
{
    fun createNewsRecord(aggregatedData: AggregatedData?)
}