package com.lsilveira.smartnews.service.impl

import com.lsilveira.smartnews.converter.StringToDateTypeConverter
import com.lsilveira.smartnews.exception.NewsException
import com.lsilveira.smartnews.model.aggregator.news.AggregatedData
import com.lsilveira.smartnews.repository.AggregatedDataRepository
import com.lsilveira.smartnews.repository.NewsRepository
import com.lsilveira.smartnews.service.NewsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.function.Consumer

@Service
class NewsServiceImpl : NewsService
{
    private val logger: Logger = LoggerFactory.getLogger(StringToDateTypeConverter::class.java)

    @Autowired
    private lateinit var aggregatedDataRepository: AggregatedDataRepository

    @Autowired
    private lateinit var newsRepository: NewsRepository

    override fun createNewsRecord(aggregatedData: AggregatedData?)
    {
        if (aggregatedData == null)
        {
            throw NewsException("Empty aggregated data!")
        }
        
        val news = aggregatedData.feed

        aggregatedDataRepository.save(aggregatedData)

        news.map { article -> article.aggregatedData = aggregatedData }
        news.forEach(Consumer { article -> newsRepository.save(article) })
        
        logger.info("News saved successfully!")
    }
}