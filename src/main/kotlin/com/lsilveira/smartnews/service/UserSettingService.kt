package com.lsilveira.smartnews.service

import com.lsilveira.smartnews.model.aggregator.AggregatorType
import com.lsilveira.smartnews.model.settings.AggregatorMapping
import com.lsilveira.smartnews.model.settings.UserSetting

/**
 * User setting service
 */
interface UserSettingService
{
    /**
     * Add a new aggregator to the user settings
     */
    fun addAggregator(username: String, aggregatorType: AggregatorType, url: String, topic: String)
            : AggregatorMapping

    /**
     * Get or create user settings by username
     */
    fun getOrCreateUserSettings(username: String) : UserSetting

    /**
     * Get or create user settings by username
     */
    fun getUserSettings(username: String) : UserSetting

    /**
     * Get all aggregators
     */
    fun getAggregators(username: String): List<AggregatorMapping>

    /**
     * Get aggregator mapping by ID
     */
    fun getAggregatorMapping(id: Long): AggregatorMapping?
}