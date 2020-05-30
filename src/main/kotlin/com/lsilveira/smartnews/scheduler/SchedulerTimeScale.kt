package com.lsilveira.smartnews.scheduler

enum class SchedulerTimeScale(val scale: Int, val description: String)
{
    SECONDS(1000, "seconds"),
    MINUTES(60*1000, "minutes"),
    HOURS(60*60*1000, "hours"),
    DAYS(24*60*60*1000, "days");
}