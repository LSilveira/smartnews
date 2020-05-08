package com.lsilveira.smartnews.repository

import com.lsilveira.smartnews.model.aggregator.AggregatorType
import com.lsilveira.smartnews.scheduler.SchedulerConfig
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SchedulerConfigRepository : CrudRepository<SchedulerConfig, Long>
{
    override fun findAll() : List<SchedulerConfig>
    fun findByEnabled(enabled: Boolean) : List<SchedulerConfig>
}