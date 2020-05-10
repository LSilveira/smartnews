package com.lsilveira.smartnews.model.aggregator.news.rss

import com.lsilveira.smartnews.exception.GetRssDataException
import com.lsilveira.smartnews.model.aggregator.AggregatorContext
import com.lsilveira.smartnews.model.aggregator.AggregatorType
import com.lsilveira.smartnews.model.aggregator.news.AggregatedData
import com.lsilveira.smartnews.model.aggregator.news.News
import com.lsilveira.smartnews.model.aggregator.news.NewsAggregator
import com.lsilveira.smartnews.model.aggregator.news.NewsStatus
import com.lsilveira.smartnews.service.NewsService
import com.lsilveira.smartnews.service.SchedulerConfigService
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

    @Autowired
    private lateinit var schedulerConfigService: SchedulerConfigService

    @Autowired
    private lateinit var newsService: NewsService

    private var restTemplate = RestTemplate()

    override fun aggregate(context: AggregatorContext): AggregatedData?
    {
        val schedulerConfig = schedulerConfigService.getSchedulerConfig(context.schedulerConfigId)
                ?:throw GetRssDataException("Scheduler config id does not exist.")

        if (!schedulerConfig.aggregatorMapping.available) // TODO add a test for this validation
        {
            throw GetRssDataException("Aggregator was disabled.")
        }

        val feed = getRssData(schedulerConfig.aggregatorMapping.url)

        val validatedFeed = validateData(feed, context.repeated)

        if (validatedFeed.isEmpty())
            return null

        return AggregatedData(feed?.title?:"",
                feed?.language?:"",
                feed?.description?:"",
                feed?.publishedDate!!,
                validatedFeed,
                schedulerConfig
        )
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

    private fun validateData(feed: SyndFeed?, repeated: Boolean) : List<News>
    {
        val feedData = feed
                ?.entries
                ?.filter { syndEntry -> repeated || !newsService.checkIfNewsAreDuplicated(syndEntry) }
                ?.map { syndEntry -> News(
                        syndEntry.title,
                        syndEntry?.description?.value?:"",
                        syndEntry?.link?:"",
                        syndEntry?.source?.link?:"",
                        syndEntry.publishedDate,
                        syndEntry.uri,
                        NewsStatus.RAW)
                }
                .orEmpty()

        return if (feedData.isNotEmpty())
            feedData
        else
            emptyList()
    }
}