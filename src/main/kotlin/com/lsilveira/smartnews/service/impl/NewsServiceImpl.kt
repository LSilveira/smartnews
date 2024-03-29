package com.lsilveira.smartnews.service.impl

import com.lsilveira.smartnews.exception.NewsException
import com.lsilveira.smartnews.model.aggregator.news.AggregatedData
import com.lsilveira.smartnews.model.aggregator.news.News
import com.lsilveira.smartnews.model.aggregator.news.NewsStatus
import com.lsilveira.smartnews.model.aggregator.news.ProcessedNews
import com.lsilveira.smartnews.repository.AggregatedDataRepository
import com.lsilveira.smartnews.repository.AggregatorMappingRepository
import com.lsilveira.smartnews.repository.NewsRepository
import com.lsilveira.smartnews.service.NewsService
import com.lsilveira.smartnews.service.UserSettingService
import com.lsilveira.smartnews.util.TextProcessingHelper
import com.rometools.rome.feed.synd.SyndEntry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.function.Consumer

@Service
class NewsServiceImpl : NewsService
{
    @Autowired
    private lateinit var aggregatedDataRepository: AggregatedDataRepository

    @Autowired
    private lateinit var newsRepository: NewsRepository

    @Autowired
    private lateinit var aggregatorMappingRepository: AggregatorMappingRepository

    @Autowired
    private lateinit var userSettingService: UserSettingService

    override fun createNewsRecord(aggregatedData: AggregatedData?)
    {
        if (aggregatedData == null)
        {
            throw NewsException("Empty aggregated data!")
        }
        
        val news = aggregatedData.feed

        aggregatedDataRepository.save(aggregatedData)

        news.map { article -> article.aggregatedData = aggregatedData }
        news.map { cleanNews(it) }
        news.forEach(Consumer { article -> newsRepository.save(article) })
    }

    override fun checkIfNewsAreDuplicated(syndEntry: SyndEntry): Boolean
    {
        val news = newsRepository.findByUriAndTitleAndLinkAndSourceAndPublishedDate(syndEntry.uri,
                syndEntry.title, syndEntry.link, syndEntry.source?.title?:"",
                syndEntry.publishedDate)

        return news.isNotEmpty()
    }

    override fun getData(aggregatorMappingId: Long): List<News>
    {
        val aggregatorMapping = userSettingService.getAggregatorMapping(aggregatorMappingId)
                ?:throw NewsException("$aggregatorMappingId is an invalid aggregator mapping ID!")

        return aggregatorMapping.schedulerConfigs
                .flatMap { schedulerConfig -> schedulerConfig.aggregatedDataList }
                .flatMap { aggregatedData -> aggregatedData.feed }
    }

    override fun cleanAllData(aggregatorId: Long)
    {
        val aggregatorMapping = userSettingService.getAggregatorMapping(aggregatorId)
                ?:throw NewsException("$aggregatorId is an invalid aggregator mapping ID!")

        aggregatorMapping.schedulerConfigs
                .flatMap { schedulerConfig -> schedulerConfig.aggregatedDataList }
                .forEach { aggregatedData -> aggregatedData.feed
                        .forEach { news -> cleanNews(news) } }

        aggregatorMappingRepository.save(aggregatorMapping)
    }

    private fun cleanNews(news: News): News
    {
        if ( news.status == NewsStatus.RAW )
        {
            news.status = NewsStatus.CLEANED
            news.processedNews = ProcessedNews(
                    TextProcessingHelper.cleanHtml(news.title)?:"",
                    TextProcessingHelper.cleanHtml(news.description)?:"",
                    null
            )
        }

        return news
    }
}