package com.lsilveira.smartnews.model.aggregator

import com.lsilveira.smartnews.model.aggregator.news.AggregatedData

/**
 * Aggregation interface
 */
interface Aggregator
{
    /**
     * Start aggregation task
     */
    fun aggregate(context: AggregatorContext) : AggregatedData

    /**
     * Get aggregator type
     */
    fun getType(): AggregatorType
}