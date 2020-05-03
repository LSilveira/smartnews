package com.lsilveira.smartnews.repository

import com.lsilveira.smartnews.model.aggregator.news.News
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface NewsRepository : CrudRepository<News, Long>
{
    fun findByUriAndTitleAndLinkAndSourceAndPublishedDate(uri: String, title: String,
                                                          link: String, source:
                                                          String, publishedDate: Date): List<News>
}