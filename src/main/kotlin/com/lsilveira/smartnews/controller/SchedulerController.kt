package com.lsilveira.smartnews.controller

import com.lsilveira.smartnews.exception.SchedulerException
import com.lsilveira.smartnews.form.SchedulerForm
import com.lsilveira.smartnews.model.aggregator.AggregatorType
import com.lsilveira.smartnews.scheduler.SchedulerTimeScale
import com.lsilveira.smartnews.scheduler.SchedulerType
import com.lsilveira.smartnews.service.ScheduleService
import com.lsilveira.smartnews.service.UserSettingService
import com.lsilveira.smartnews.util.DateHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import javax.validation.Valid

@Controller
@RequestMapping("/scheduler")
class SchedulerController
{
    @Autowired
    private lateinit var userSettingService: UserSettingService

    @Autowired
    private lateinit var scheduleService: ScheduleService

    @GetMapping
    fun index() : ModelAndView
    {
        val view = ModelAndView("scheduler/home")
        val userName = SecurityContextHolder.getContext().authentication.name

        view.addObject("schedulerForm", SchedulerForm())
        view.addObject("aggregatorMappings",
                userSettingService.getAggregators(SecurityContextHolder.getContext()
                        .authentication
                        .name))
        view.addObject("schedulerTypes", SchedulerType.values()
                .map { schedulerType -> schedulerType.name })
        view.addObject("aggregatorTypes", AggregatorType.values()
                .map { aggregatorType -> aggregatorType.name })
        view.addObject("timeScales", SchedulerTimeScale.values()
                .map { schedulerTimeScale -> schedulerTimeScale.name })
        view.addObject("scheduledTasks", scheduleService.getUserSchedules(userName))

        return view
    }

    @PostMapping
    fun createSchedule(@Valid @ModelAttribute("schedulerForm") schedulerForm: SchedulerForm,
                  bindingResult: BindingResult) : ModelAndView
    {
        val view: ModelAndView

        val userName = SecurityContextHolder.getContext().authentication.name
        val aggregatorUser = userSettingService.getAggregatorMapping(schedulerForm.aggregatorMappingId)
                ?.userSetting?.username?:throw SchedulerException("Invalid aggregator!")

        if (userName != aggregatorUser)
        {
            bindingResult.addError(ObjectError("aggregatorMappingId",
                    "Selected aggregator is not valid for the current user!"))
        }

        val createSchedule: () -> Unit = {
            when (val schedulerType = SchedulerType.valueOf(schedulerForm.schedulerType))
            {
                SchedulerType.ONCE -> createSingleSchedule(schedulerForm, schedulerType, bindingResult)
                SchedulerType.REPEATABLE -> createMultipleSchedules(schedulerForm, schedulerType, bindingResult)
            }
        }

        if (bindingResult.hasErrors())
        {
            view = ModelAndView("scheduler/home")
        }
        else
        {
            view = ModelAndView("redirect:/scheduler")

            createSchedule()
        }

        return view
    }

    private fun createSingleSchedule(schedulerForm: SchedulerForm, schedulerType: SchedulerType,
                                     bindingResult: BindingResult)
    {
        val date = schedulerForm.date?.date
        if (date == null)
        {
            bindingResult.addError(ObjectError("date", "Invalid date format!"))
        }

        val time = schedulerForm.time?.time
        if (time == null)
        {
            bindingResult.addError(ObjectError("time", "Invalid time format!"))
        }

        val dateTime = DateHelper.combineDateTime(date!!, time!!)

        if (dateTime == null)
        {
            bindingResult.addError(ObjectError("time", "Invalid time format!"))
        }

        scheduleService.createScheduledTask(schedulerForm.aggregatorMappingId, schedulerType,
                dateTime!!, schedulerForm.aggregatorType)
    }

    private fun createMultipleSchedules(schedulerForm: SchedulerForm, schedulerType: SchedulerType,
                                        bindingResult: BindingResult)
    {
        val timeUnit = schedulerForm.timeUnit
        if (timeUnit == null)
        {
            bindingResult.addError(ObjectError("timeUnit", "Invalid time unit format!"))
        }

        val timeScale = schedulerForm.timeScale
        if (timeScale == null)
        {
            bindingResult.addError(ObjectError("timeScale", "Invalid time scale format!"))
        }

        scheduleService.createScheduledTask(schedulerForm.aggregatorMappingId, schedulerType,
                timeUnit!!, timeScale!!, schedulerForm.aggregatorType)
    }
}