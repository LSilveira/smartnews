package com.lsilveira.smartnews.model.aggregator

data class AggregatorContext
(
        val schedulerConfigId: Long,
        val repeated: Boolean = true
)