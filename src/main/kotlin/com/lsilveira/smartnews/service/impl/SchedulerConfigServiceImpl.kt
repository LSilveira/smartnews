package com.lsilveira.smartnews.service.impl

import com.lsilveira.smartnews.model.aggregator.AggregatorType
import com.lsilveira.smartnews.repository.SchedulerConfigRepository
import com.lsilveira.smartnews.scheduler.SchedulerConfig
import com.lsilveira.smartnews.service.SchedulerConfigService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SchedulerConfigServiceImpl : SchedulerConfigService
{
    @Autowired
    private lateinit var schedulerConfigRepository: SchedulerConfigRepository

    override fun readActiveTasks(): List<SchedulerConfig>
    {
        return schedulerConfigRepository.findByEnabled(true)
    }

    override fun getByAggregatorType(aggregatorType: AggregatorType): SchedulerConfig?
    {
        return schedulerConfigRepository.findByAggregatorType(aggregatorType)
    }
}