package com.lsilveira.smartnews.scheduler

import com.lsilveira.smartnews.model.aggregator.Aggregator
import com.lsilveira.smartnews.model.settings.AggregatorMapping
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
        val aggregator = schedulerConfig.aggregatorType.getAggregator()

        if (schedulerConfig.enabled)
        {
            when (schedulerConfig.type)
            {
                SchedulerType.ONCE -> newsSingleTask(schedulerConfig.aggregatorMapping,
                        aggregator, schedulerConfig.date?:throw SchedulingException("Invalid date!"))
                SchedulerType.REPEATABLE -> newsRepeatableTask(schedulerConfig.aggregatorMapping,
                        aggregator, schedulerConfig.timeUnit?:throw SchedulingException("Invalid time unit!"),
                        schedulerConfig.timeScale?:throw SchedulingException("Invalid time scale!"))
            }
        }
        else
        {
            logger.info("[Scheduled-Task-Disabled] \"${schedulerConfig.aggregatorMapping.category}\" is disabled!")
        }
    }

    fun deleteSchedule(schedulerConfig: SchedulerConfig)
    {

    }

    private fun newsSingleTask(aggregatorMapping: AggregatorMapping, aggregator: Aggregator, date: Date)
    {
        executor.schedule(AggregateNewsTask(newsService, aggregatorMapping, aggregator), date)
        logger.info("[Scheduled-Single-Task] $date ${aggregatorMapping.category}")
    }

    private fun newsRepeatableTask(aggregatorMapping: AggregatorMapping, aggregator: Aggregator,
                                   timeUnit: Long,
                                   timeScale: SchedulerTimeScale)
    {
        executor.scheduleAtFixedRate(AggregateNewsTask(newsService, aggregatorMapping, aggregator),
                timeUnit * timeScale.scale)
        logger.info("[Scheduled-Repeatable-Task] $timeUnit ${aggregatorMapping.category}")
    }
}