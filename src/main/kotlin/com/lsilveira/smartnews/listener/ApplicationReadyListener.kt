package com.lsilveira.smartnews.listener

import com.lsilveira.smartnews.service.ScheduleService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class ApplicationReadyListener : ApplicationListener<ApplicationReadyEvent>
{
    private val logger: Logger = LoggerFactory.getLogger(ApplicationReadyListener::class.java)

    @Autowired
    private lateinit var scheduleService: ScheduleService

    override fun onApplicationEvent(event: ApplicationReadyEvent)
    {
        logger.info("Started loading components...")

        scheduleService.loadScheduledTasks()

        logger.info("Finished loading components.")
    }
}