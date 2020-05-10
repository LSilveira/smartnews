package com.lsilveira.smartnews.service

import com.lsilveira.smartnews.model.aggregator.news.AggregatedData
import com.lsilveira.smartnews.model.aggregator.news.News
import com.rometools.rome.feed.synd.SyndEntry

/**
 * Service to manage news
 */
interface NewsService
{
    /**
     * Create news record
     */
    fun createNewsRecord(aggregatedData: AggregatedData?)

    /**
     * Check if news data already exist
     */
    fun checkIfNewsAreDuplicated(syndEntry: SyndEntry): Boolean

    /**
     * Get news data
     */
    fun getData(aggregatorMappingId: Long): List<News>

    /**
     * Clean all data by aggregator id
     */
    fun cleanAllData(aggregatorId: Long)
}