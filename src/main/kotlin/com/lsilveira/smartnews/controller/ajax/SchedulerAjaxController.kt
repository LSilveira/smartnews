package com.lsilveira.smartnews.controller.ajax

import com.lsilveira.smartnews.request.ScheduleStateRequest
import com.lsilveira.smartnews.response.ScheduleStateResponse
import com.lsilveira.smartnews.service.ScheduleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ajax/scheduler")
class SchedulerAjaxController
{
    @Autowired
    private lateinit var scheduleService: ScheduleService

    @PostMapping("/state")
    fun changeScheduleState(@RequestBody scheduleStateRequest: ScheduleStateRequest) : ScheduleStateResponse
    {
        val userName = SecurityContextHolder.getContext().authentication.name
        val aggregatorUser = scheduleService.getSchedulerConfig(scheduleStateRequest.schedulerConfigId)
                ?.aggregatorMapping?.userSetting?.username?:return ScheduleStateResponse("Invalid schedule config!")

        if (userName != aggregatorUser)
        {
            ScheduleStateResponse("Selected aggregator is not valid for the current user!")
        }

        if (scheduleStateRequest.enabled)
        {
            scheduleService.scheduleTask(scheduleStateRequest.schedulerConfigId)
        }
        else
        {
            scheduleService.unscheduleTask(scheduleStateRequest.schedulerConfigId)
        }

        return ScheduleStateResponse("")
    }
}