package com.lsilveira.smartnews.model.settings

import com.lsilveira.smartnews.model.aggregator.AggregatorType
import javax.persistence.*

/**
 * User aggregator mapping
 */
@Entity
@Table(name = "SN_AGGREGATOR_MAPPING")
data class AggregatorMapping
(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "USER_SETTING_ID")
        val userSetting: UserSetting,

        val aggregatorType: AggregatorType = AggregatorType.RSS, // default aggregator
        val url: String,
        val category: String,
        val available: Boolean,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0
)