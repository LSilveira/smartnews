package com.lsilveira.smartnews.model.aggregator.news

import com.lsilveira.smartnews.scheduler.SchedulerConfig
import com.rometools.rome.feed.synd.SyndFeed
import java.util.*

//TODO redundant
data class NewsModel(val title: String, val language: String, val description: String,
                val publishedDate: Date, val feed: SyndFeed,
                val schedulerConfig: SchedulerConfig)