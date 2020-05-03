package com.lsilveira.smartnews.service

import com.lsilveira.smartnews.scheduler.SchedulerConfig
import com.lsilveira.smartnews.scheduler.SchedulerTimeScale
import com.lsilveira.smartnews.scheduler.SchedulerType
import java.util.*

/**
 * Schedule service
 */
interface ScheduleService
{
    /**
     * Create a new scheduled task
     */
    fun createScheduledTask(aggregatorMappingId: Long, schedulerType: SchedulerType, timeUnit: Long,
                            timeScale: SchedulerTimeScale, aggregatorType: String)

    /**
     * Create a new scheduled task
     */
    fun createScheduledTask(aggregatorMappingId: Long, schedulerType: SchedulerType, date: Date,
                            aggregatorType: String)

    /**
     * Schedule a task
     */
    fun scheduleTask(schedulerConfigId: Long)

    /**
     * Unschedule a task
     */
    fun unscheduleTask(schedulerConfigId: Long)

    /**
     * Get User schedules
     */
    fun getUserSchedules(username: String): List<SchedulerConfig>

    /**
     * Get scheduler config
     */
    fun getSchedulerConfig(schedulerConfigId: Long): SchedulerConfig?
}