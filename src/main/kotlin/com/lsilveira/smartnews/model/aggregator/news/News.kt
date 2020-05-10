package com.lsilveira.smartnews.model.aggregator.news

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

/**
 * News
 */
@Entity
@Table(name = "SN_NEWS")
data class News
(
//        val guid: Long,
        var title: String,

        @Type(type="text")
        var description: String,

        var link: String,
        var source: String,
        var publishedDate: Date,
        var uri: String,
        var status: NewsStatus,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0
)
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AGGREGATED_DATA_ID")
    lateinit var aggregatedData: AggregatedData

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "PROCESSED_NEWS_ID")
    var processedNews: ProcessedNews? = null
}