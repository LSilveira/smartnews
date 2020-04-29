package com.lsilveira.smartnews.service.impl

import com.lsilveira.smartnews.exception.EmptyInputDataException
import com.lsilveira.smartnews.exception.UserSettingsException
import com.lsilveira.smartnews.model.aggregator.Aggregator
import com.lsilveira.smartnews.model.aggregator.AggregatorType
import com.lsilveira.smartnews.model.settings.AggregatorMapping
import com.lsilveira.smartnews.model.settings.UserSetting
import com.lsilveira.smartnews.repository.AggregatorMappingRepository
import com.lsilveira.smartnews.repository.UserSettingRepository
import com.lsilveira.smartnews.service.UserSettingService
import com.lsilveira.smartnews.util.UrlHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserSettingServiceImpl : UserSettingService
{
    @Autowired
    private lateinit var userSettingRepository: UserSettingRepository

    @Autowired
    private lateinit var aggregatorMappingRepository: AggregatorMappingRepository

    override fun addAggregator(username: String, aggregatorType: AggregatorType, url: String, category: String)
            : AggregatorMapping
    {
        if (url.isBlank() || !UrlHelper.validateUrl(url))
        {
            throw EmptyInputDataException("Aggregator cannot have an invalid url.")
        }

        if (category.isBlank())
        {
            throw EmptyInputDataException("Aggregator cannot have an empty category.")
        }

        val userSetting = userSettingRepository.findByUsername(username)
                ?:throw EmptyInputDataException("Aggregator cannot have an empty user setting.")
        val aggregatorMapping = AggregatorMapping(userSetting, aggregatorType, url, category, true)

        return aggregatorMappingRepository.save(aggregatorMapping)
    }

    override fun getOrCreateUserSettings(username: String) : UserSetting
    {
        if (username.isEmpty())
        {
            throw EmptyInputDataException("Username cannot be empty.")
        }

        return userSettingRepository.findByUsername(username).takeIf { it != null }
                ?: createUserSettings(username)
    }

    override fun getAggregators(username: String): List<AggregatorMapping>
    {
        return userSettingRepository.findByUsername(username)?.aggregatorMappings?.toList()
                ?:throw UserSettingsException("Invalid user name!")
    }

    override fun getAggregatorMapping(id: Long): AggregatorMapping?
    {
        return aggregatorMappingRepository.findById(id).orElse(null)
    }

    private fun createUserSettings(username: String) : UserSetting
    {
        return userSettingRepository.save(UserSetting(username, ""))
    }
}