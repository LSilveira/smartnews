package com.lsilveira.smartnews.service

import com.lsilveira.smartnews.model.aggregator.AggregatorType
import com.lsilveira.smartnews.scheduler.SchedulerConfig

/**
 * Scheduler config service
 */
interface SchedulerConfigService
{
    /**
     * Gets all active tasks in the scheduler
     */
    fun readActiveTasks(): List<SchedulerConfig>

    /**
     * Get Scheduler Config by the Aggregator Type
     */
    fun getByAggregatorType(aggregatorType: AggregatorType): SchedulerConfig?
}