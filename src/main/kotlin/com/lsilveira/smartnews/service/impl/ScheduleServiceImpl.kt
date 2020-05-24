package com.lsilveira.smartnews.service.impl

import com.lsilveira.smartnews.exception.SchedulerException
import com.lsilveira.smartnews.repository.SchedulerConfigRepository
import com.lsilveira.smartnews.scheduler.Scheduler
import com.lsilveira.smartnews.scheduler.SchedulerConfig
import com.lsilveira.smartnews.scheduler.SchedulerTimeScale
import com.lsilveira.smartnews.scheduler.SchedulerType
import com.lsilveira.smartnews.service.ScheduleService
import com.lsilveira.smartnews.service.UserSettingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ScheduleServiceImpl : ScheduleService
{
    @Autowired
    private lateinit var scheduler: Scheduler

    @Autowired
    private lateinit var schedulerConfigRepository: SchedulerConfigRepository

    @Autowired
    private lateinit var userSettingService: UserSettingService

    override fun createScheduledTask(aggregatorMappingId: Long, schedulerType: SchedulerType,
                                     cleanData: Boolean, timeUnit: Long, timeScale: SchedulerTimeScale)
    {
        val aggregatorMapping = userSettingService.getAggregatorMapping(aggregatorMappingId)
                ?:throw SchedulerException("$aggregatorMappingId is an invalid aggregator mapping ID!")

        val schedulerConfig = SchedulerConfig(aggregatorMapping, schedulerType, cleanData, timeUnit,
                timeScale, null, false)

        schedulerConfigRepository.save(schedulerConfig)
    }

    override fun createScheduledTask(aggregatorMappingId: Long, schedulerType: SchedulerType,
                                     cleanData: Boolean, date: Date)
    {
        val aggregatorMapping = userSettingService.getAggregatorMapping(aggregatorMappingId)
                ?:throw SchedulerException("$aggregatorMappingId is an invalid aggregator mapping ID!")

        val schedulerConfig = SchedulerConfig(aggregatorMapping, schedulerType, cleanData, null,
                null, date, false)

        schedulerConfigRepository.save(schedulerConfig)
    }

    override fun scheduleTask(schedulerConfigId: Long)
    {
        val schedulerConfig = schedulerConfigRepository.findById(schedulerConfigId)
                .orElseThrow { SchedulerException("$schedulerConfigId is an invalid scheduler config ID!") }

        schedulerConfig.enabled = true
        schedulerConfigRepository.save(schedulerConfig)

        scheduler.runSchedule(schedulerConfig)
    }

    override fun unscheduleTask(schedulerConfigId: Long)
    {
        val schedulerConfig = schedulerConfigRepository.findById(schedulerConfigId)
                .orElseThrow { SchedulerException("$schedulerConfigId is an invalid scheduler config ID!") }

        schedulerConfig.enabled = false
        schedulerConfigRepository.save(schedulerConfig)

        scheduler.deleteSchedule(schedulerConfig)
    }

    override fun getUserSchedules(username: String): List<SchedulerConfig>
    {
        return schedulerConfigRepository.findAll()
                .filter { schedulerConfig ->
                    schedulerConfig.aggregatorMapping.userSetting.username == username
                }
    }

    override fun getSchedulerConfig(schedulerConfigId: Long): SchedulerConfig?
    {
        return schedulerConfigRepository.findById(schedulerConfigId)
                .orElse(null)
    }

    override fun loadScheduledTasks()
    {
        val schedulerConfig = schedulerConfigRepository.findByEnabled(true)
        schedulerConfig.forEach { scheduler.runSchedule(it) }
    }
}