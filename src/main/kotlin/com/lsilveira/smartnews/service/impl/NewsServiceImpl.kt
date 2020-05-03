package com.lsilveira.smartnews.service.impl

import com.lsilveira.smartnews.exception.NewsException
import com.lsilveira.smartnews.model.aggregator.news.AggregatedData
import com.lsilveira.smartnews.repository.AggregatedDataRepository
import com.lsilveira.smartnews.repository.NewsRepository
import com.lsilveira.smartnews.service.NewsService
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
    }

    override fun checkIfNewsAreDuplicated(syndEntry: SyndEntry): Boolean
    {
        val news = newsRepository.findByUriAndTitleAndLinkAndSourceAndPublishedDate(syndEntry.uri,
                syndEntry.title, syndEntry.link, syndEntry.source.link, syndEntry.publishedDate)

        return news.isNotEmpty()
    }
}