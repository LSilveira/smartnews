package com.lsilveira.smartnews.model.aggregator

import com.lsilveira.smartnews.model.aggregator.news.rss.RssAggregator
import com.lsilveira.smartnews.util.BeanUtil

enum class AggregatorType(name: String)
{
    RSS("RSS")
    {
        override fun getAggregator(): Aggregator
        {
            return BeanUtil.getBean(RssAggregator::class.java)
        }
    };

    abstract fun getAggregator(): Aggregator
}