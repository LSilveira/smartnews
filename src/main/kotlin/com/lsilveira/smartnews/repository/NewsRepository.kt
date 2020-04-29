package com.lsilveira.smartnews.repository

import com.lsilveira.smartnews.model.aggregator.news.News
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface NewsRepository : CrudRepository<News, Long>
{
}