package com.lsilveira.smartnews.scheduler

import com.lsilveira.smartnews.model.aggregator.AggregatorType
import com.lsilveira.smartnews.model.aggregator.news.AggregatedData
import com.lsilveira.smartnews.model.settings.AggregatorMapping
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "SN_SCHEDULER_CONFIG")
data class SchedulerConfig
(
        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "AGGREGATOR_MAPPING_ID")
        val aggregatorMapping: AggregatorMapping,

        val type: SchedulerType,
        val timeUnit: Long?,
        val timeScale: SchedulerTimeScale?,
        val date: Date?,
//        val wildcard: String,
        val aggregatorType: AggregatorType,
        var enabled: Boolean,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0
)
{
        @OneToMany(mappedBy = "scheduleConfig", cascade = [CascadeType.ALL], orphanRemoval = true,
                fetch = FetchType.LAZY)
        var aggregatedDataList: List<AggregatedData> = mutableListOf()
}