package com.lsilveira.smartnews.scheduler

enum class SchedulerTimeScale(val scale: Int)
{
    SECONDS(1000),
    MINUTES(60*1000),
    HOURS(60*60*1000),
    DAYS(24*60*60*1000);
}