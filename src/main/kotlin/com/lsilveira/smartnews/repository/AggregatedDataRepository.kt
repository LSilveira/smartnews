package com.lsilveira.smartnews.repository

import com.lsilveira.smartnews.model.aggregator.news.AggregatedData
import org.springframework.data.repository.CrudRepository

interface AggregatedDataRepository : CrudRepository<AggregatedData, Long>
{
}