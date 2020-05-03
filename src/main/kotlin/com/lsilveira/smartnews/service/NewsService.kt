package com.lsilveira.smartnews.service

import com.lsilveira.smartnews.model.aggregator.news.AggregatedData
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
}