package com.lsilveira.smartnews.model.aggregator.news

import com.lsilveira.smartnews.scheduler.SchedulerConfig
import java.util.*
import javax.persistence.*

/**
 * Aggregated data
 */
@Entity
@Table(name = "SN_AGGREGATED_DATA")
data class AggregatedData
(
        var title: String,
        var language: String,
        var description: String,
        var lastBuildDate: Date,

        @OneToMany(mappedBy = "aggregatedData", cascade = [CascadeType.ALL], orphanRemoval = true,
                fetch = FetchType.LAZY)
        var feed: List<News> = mutableListOf(),

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "SCHEDULER_CONFIG_ID")
        var scheduleConfig: SchedulerConfig,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0
)