package com.lsilveira.smartnews.service.impl.integration

import com.lsilveira.smartnews.exception.EmptyInputDataException
import com.lsilveira.smartnews.model.aggregator.AggregatorType
import com.lsilveira.smartnews.service.UserSettingService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(SpringExtension::class)
internal class UserSettingServiceImplTest
{
    private val DB_USER_SETTING_ID = 2L
    private val DB_USERNAME = "username"
    private val DB_PASSWORD = "password"
    private val USERNAME = "username2"
    private val AGGREGGATOR_TYPE = AggregatorType.RSS
    private val TOPIC = "News"
    private val URL = "www.smartnews.com"

    @Autowired
    private lateinit var userSettingService: UserSettingService

    @BeforeEach
    fun setUp()
    {
    }

    @Test
    fun addAggregatorWithEmptyUrl()
    {
        Assertions.assertThrows(EmptyInputDataException::class.java)
        {
            userSettingService.addAggregator(DB_USERNAME, AGGREGGATOR_TYPE, "", TOPIC)
        }
    }

    @Test
    fun addAggregatorWithEmptyUserSettings()
    {
        Assertions.assertThrows(EmptyInputDataException::class.java)
        {
            userSettingService.addAggregator("", AGGREGGATOR_TYPE, URL, TOPIC)
        }
    }

    @Test
    fun addValidAggregator()
    {
        val aggregatorMapping = userSettingService.addAggregator(DB_USERNAME, AGGREGGATOR_TYPE, URL, TOPIC)

        Assertions.assertNotNull(aggregatorMapping)
        Assertions.assertNotNull(aggregatorMapping.id)
        Assertions.assertNotNull(aggregatorMapping.userSetting)
        Assertions.assertTrue(aggregatorMapping.aggregatorType == AGGREGGATOR_TYPE)
        Assertions.assertTrue(aggregatorMapping.url == URL)
    }

    @Test
    fun createValidUser()
    {
        val userSetting = userSettingService.getOrCreateUserSettings(USERNAME)

        Assertions.assertNotNull(userSetting)
        Assertions.assertTrue(userSetting.username == USERNAME)
        Assertions.assertTrue(userSetting.password == "")
    }

    @Test
    fun getValidUser()
    {
        val userSetting = userSettingService.getOrCreateUserSettings(DB_USERNAME)

        Assertions.assertNotNull(userSetting)
        Assertions.assertTrue(userSetting.id == DB_USER_SETTING_ID)
        Assertions.assertTrue(userSetting.username == DB_USERNAME)
        Assertions.assertTrue(userSetting.password == DB_PASSWORD)
    }

    @Test
    fun getOrCreateUserWithEmptyUserName()
    {
        Assertions.assertThrows(EmptyInputDataException::class.java)
        {
            userSettingService.getOrCreateUserSettings("")
        }
    }
}