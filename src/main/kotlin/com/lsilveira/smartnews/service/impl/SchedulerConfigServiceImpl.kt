package com.lsilveira.smartnews.service.impl

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

    override fun getSchedulerConfig(schedulerConfigId: Long): SchedulerConfig?
    {
        return schedulerConfigRepository.findById(schedulerConfigId)
                .orElse(null)
    }
}