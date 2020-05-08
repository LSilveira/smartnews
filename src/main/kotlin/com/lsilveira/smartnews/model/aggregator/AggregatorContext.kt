package com.lsilveira.smartnews.model.aggregator

data class AggregatorContext
(
        val mappingId: Long,
        val repeated: Boolean = true
)