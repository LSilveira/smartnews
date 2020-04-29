package com.lsilveira.smartnews.model.aggregator.news

import java.util.*
import javax.persistence.*

/**
 * Aggregated data
 */
@Entity
@Table(name = "SN_AGGREGATED_DATA")
data class AggregatedData
(
        val title: String,
        val language: String,
        val description: String,
        val lastBuildDate: Date,

        @OneToMany(mappedBy = "aggregatedData", cascade = [CascadeType.ALL], orphanRemoval = true,
                fetch = FetchType.EAGER)
        var feed: List<News> = mutableListOf(),

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0
)