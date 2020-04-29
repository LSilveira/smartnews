package com.lsilveira.smartnews.scheduler.task

import com.lsilveira.smartnews.model.aggregator.Aggregator
import com.lsilveira.smartnews.model.aggregator.AggregatorContext
import com.lsilveira.smartnews.model.settings.AggregatorMapping
import com.lsilveira.smartnews.service.NewsService

class AggregateNewsTask
(
        val newsService: NewsService,
        val aggregatorMapping: AggregatorMapping,
        val aggregator: Aggregator
)
    : Runnable
{

    override fun run()
    {
        val context = AggregatorContext(aggregatorMapping.id)
        val data = aggregator.aggregate(context)

        newsService.createNewsRecord(data)
    }
}