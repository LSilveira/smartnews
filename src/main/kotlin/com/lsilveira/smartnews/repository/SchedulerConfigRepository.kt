package com.lsilveira.smartnews.repository

import com.lsilveira.smartnews.scheduler.SchedulerConfig
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SchedulerConfigRepository : CrudRepository<SchedulerConfig, Long>
{
    fun findByEnabled(enabled: Boolean) : List<SchedulerConfig>
    fun findByAggregatorMapping(aggregatorMappingId: Long) : SchedulerConfig?
    override fun findAll() : List<SchedulerConfig>
}