package com.lsilveira.smartnews.request

data class ScheduleStateRequest
(
        val schedulerConfigId: Long,
        val enabled: Boolean
)