package com.lsilveira.smartnews.service.impl.unit

import com.lsilveira.smartnews.exception.EmptyInputDataException
import com.lsilveira.smartnews.model.aggregator.AggregatorType
import com.lsilveira.smartnews.model.settings.AggregatorMapping
import com.lsilveira.smartnews.model.settings.UserSetting
import com.lsilveira.smartnews.repository.AggregatorMappingRepository
import com.lsilveira.smartnews.repository.UserSettingRepository
import com.lsilveira.smartnews.service.impl.UserSettingServiceImpl
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(MockitoExtension::class)
internal class UserSettingServiceImplTest
{
    private val USER_SETTING_ID = 2L
    private val USERNAME = "username"
    private val PASSWORD = "password"
    private val AGGREGGATOR_TYPE = AggregatorType.RSS
    private val CATEGORY = "News"
    private val URL = "www.smartnews.com"

    @InjectMocks
    private lateinit var userSettingService: UserSettingServiceImpl

    @Mock
    private lateinit var userSettingRepository: UserSettingRepository

    @Mock
    private lateinit var aggregatorMappingRepository: AggregatorMappingRepository

    @BeforeEach
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun addAggregatorWithEmptyUrl()
    {
        Assertions.assertThrows(EmptyInputDataException::class.java)
        {
            userSettingService.addAggregator(USERNAME, AGGREGGATOR_TYPE, "", CATEGORY)
        }
    }

    @Test
    fun addAggregatorWithEmptyUserSettings()
    {
        Assertions.assertThrows(EmptyInputDataException::class.java)
        {
            userSettingService.addAggregator("", AGGREGGATOR_TYPE, URL, CATEGORY)
        }
    }

    @Test
    fun addValidAggregator()
    {
        val mockUserSetting = UserSetting(USER_SETTING_ID, USERNAME, PASSWORD)
        whenever(userSettingRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(mockUserSetting)

        val mockAggregatorMapping = AggregatorMapping(mockUserSetting, AGGREGGATOR_TYPE, URL, CATEGORY, true)
        whenever(aggregatorMappingRepository.save(any<AggregatorMapping>())).thenReturn(mockAggregatorMapping)

        val aggregatorMapping = userSettingService.addAggregator(USERNAME, AGGREGGATOR_TYPE, URL, CATEGORY)

        Assertions.assertNotNull(aggregatorMapping)
        Assertions.assertNotNull(aggregatorMapping.id)
        Assertions.assertNotNull(aggregatorMapping.userSetting)
        Assertions.assertTrue(aggregatorMapping.aggregatorType == AGGREGGATOR_TYPE)
        Assertions.assertTrue(aggregatorMapping.url == URL)
    }

    @Test
    fun createValidUser()
    {
        val dbUserSetting = UserSetting(USERNAME, PASSWORD)
        whenever(userSettingRepository.save(any<UserSetting>())).thenReturn(dbUserSetting)
        val userSetting = userSettingService.getOrCreateUserSettings(USERNAME)

        Assertions.assertNotNull(userSetting)
        Assertions.assertTrue(userSetting.username == USERNAME)
        Assertions.assertTrue(userSetting.password == PASSWORD)
    }

    @Test
    fun getValidUser()
    {
        val dbUserSetting = UserSetting(USERNAME, PASSWORD)
        whenever(userSettingRepository.findByUsername(any<String>())).thenReturn(dbUserSetting)
        val userSetting = userSettingService.getOrCreateUserSettings(USERNAME)

        Assertions.assertNotNull(userSetting)
        Assertions.assertTrue(userSetting.username == USERNAME)
        Assertions.assertTrue(userSetting.password == PASSWORD)
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