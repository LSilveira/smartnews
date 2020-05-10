package com.lsilveira.smartnews.model.aggregator.news

import org.hibernate.annotations.Type
import javax.persistence.*

/**
 * Processed news
 */
@Entity
@Table(name = "SN_PROCESSED_NEWS")
data class ProcessedNews
(
        var title: String,

        @Type(type="text")
        var description: String,

        var sentiment: TextSentiment?,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0
)
{
        @OneToOne(mappedBy = "processedNews")
        var news: News? = null
}