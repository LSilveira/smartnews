package com.lsilveira.smartnews.scheduler.task

import com.lsilveira.smartnews.model.aggregator.Aggregator
import com.lsilveira.smartnews.model.aggregator.AggregatorContext
import com.lsilveira.smartnews.model.settings.AggregatorMapping
import com.lsilveira.smartnews.service.NewsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class AggregateNewsTask
(
        val newsService: NewsService,
        val aggregatorMapping: AggregatorMapping,
        val aggregator: Aggregator
)
    : Runnable
{
    private val logger: Logger = LoggerFactory.getLogger(AggregateNewsTask::class.java)

    override fun run()
    {
        val context = AggregatorContext(aggregatorMapping.id, false)
        val data = aggregator.aggregate(context)

        if (data != null)
        {
            newsService.createNewsRecord(data)
            logger.info("Aggregator ${aggregatorMapping.id} collected ${data.feed.size} news!")
        }
        else
        {
            logger.info("Aggregator ${aggregatorMapping.id} has no new data to collect!")
        }
    }
}