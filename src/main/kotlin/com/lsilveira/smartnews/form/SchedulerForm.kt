package com.lsilveira.smartnews.form

import com.lsilveira.smartnews.scheduler.SchedulerTimeScale
import java.util.*

class SchedulerForm
{
    var aggregatorMappingId: Long = 0
    var schedulerType: String = ""
    var timeUnit: Long? = 1
    var timeScale: SchedulerTimeScale? = SchedulerTimeScale.HOURS
    var cleanData: Boolean = false
    var date: DateType? = DateType(Date())
    var time: TimeType? = TimeType(Date())
}