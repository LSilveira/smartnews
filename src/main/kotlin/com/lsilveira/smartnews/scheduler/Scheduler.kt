package com.lsilveira.smartnews.scheduler

import com.lsilveira.smartnews.model.aggregator.Aggregator
import com.lsilveira.smartnews.scheduler.task.AggregateNewsTask
import com.lsilveira.smartnews.service.NewsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.SchedulingException
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Component
import java.util.*

@Component
class Scheduler
{
    private val logger: Logger = LoggerFactory.getLogger(Scheduler::class.java)

    @Autowired
    private lateinit var executor: TaskScheduler

    @Autowired
    private lateinit var newsService: NewsService

    fun runSchedule(schedulerConfig: SchedulerConfig)
    {
        val aggregator = schedulerConfig.aggregatorMapping.aggregatorType.getAggregator()

        if (schedulerConfig.enabled)
        {
            when (schedulerConfig.type)
            {
                SchedulerType.ONCE -> newsSingleTask(schedulerConfig, aggregator,
                        schedulerConfig.date?:throw SchedulingException("Invalid date!"))
                SchedulerType.REPEATABLE -> newsRepeatableTask(schedulerConfig, aggregator,
                        schedulerConfig.timeUnit?:throw SchedulingException("Invalid time unit!"),
                        schedulerConfig.timeScale?:throw SchedulingException("Invalid time scale!"))
            }
        }
        else
        {
            logger.info("[Scheduled-Task-Disabled] \"${schedulerConfig.aggregatorMapping.topic}\" is disabled!")
        }
    }

    fun deleteSchedule(schedulerConfig: SchedulerConfig)
    {

    }

    private fun newsSingleTask(schedulerConfig: SchedulerConfig, aggregator: Aggregator, date: Date)
    {
        executor.schedule(AggregateNewsTask(newsService, schedulerConfig, aggregator), date)
        logger.info("[Scheduled-Single-Task] $date ${schedulerConfig.aggregatorMapping.topic}")
    }

    private fun newsRepeatableTask(schedulerConfig: SchedulerConfig, aggregator: Aggregator,
                                   timeUnit: Long,
                                   timeScale: SchedulerTimeScale)
    {
        executor.scheduleAtFixedRate(AggregateNewsTask(newsService, schedulerConfig, aggregator),
                timeUnit * timeScale.scale)
        logger.info("[Scheduled-Repeatable-Task] $timeUnit ${timeScale.description} ${schedulerConfig.aggregatorMapping.topic}")
    }
}