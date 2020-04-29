package com.lsilveira.smartnews.model.aggregator.news.rss

import com.lsilveira.smartnews.exception.GetRssDataException
import com.lsilveira.smartnews.model.aggregator.AggregatorContext
import com.lsilveira.smartnews.model.aggregator.AggregatorType
import com.lsilveira.smartnews.model.aggregator.news.AggregatedData
import com.lsilveira.smartnews.model.aggregator.news.News
import com.lsilveira.smartnews.model.aggregator.news.NewsAggregator
import com.lsilveira.smartnews.service.UserSettingService
import com.lsilveira.smartnews.util.UrlHelper
import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.io.FeedException
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.client.ResponseExtractor
import org.springframework.web.client.RestTemplate

@Component
class RssAggregator: NewsAggregator
{
    @Autowired
    private lateinit var userSettingService: UserSettingService

    private var restTemplate = RestTemplate()

    override fun aggregate(context: AggregatorContext): AggregatedData
    {
        val aggregatorMapping = userSettingService.getAggregatorMapping(context.mappingId)
                ?:throw GetRssDataException("Aggregator id does not exist.")

        if (!aggregatorMapping.available) // TODO add a test for this validation
        {
            throw GetRssDataException("Aggregator was disabled.")
        }

        val feed = getRssData(aggregatorMapping.url)

        return AggregatedData(feed?.title?:"",
                feed?.language?:"",
                feed?.description?:"",
                feed?.publishedDate!!,
                feed
                .entries
                ?.map { syndEntry -> News(
                        syndEntry.title,
                        syndEntry?.description?.value?:"",
                        syndEntry?.link?:"",
                        syndEntry?.source?.link?:"",
                        syndEntry.publishedDate)
                }
                .orEmpty())
    }

    override fun getType(): AggregatorType
    {
        return AggregatorType.RSS
    }

    private fun getRssData(url: String) : SyndFeed?
    {
        if (url.isBlank() || !UrlHelper.validateUrl(url))
        {
            throw GetRssDataException("Url is not valid.")
        }

        return restTemplate.execute(
                url, HttpMethod.GET, null, ResponseExtractor { response: ClientHttpResponse ->
            val input = SyndFeedInput()
            try
            {
                input.build(XmlReader(response.body))
            }
            catch (e: FeedException)
            {
                throw GetRssDataException("Response could not be parsed.", e)
            }
        })
    }
}