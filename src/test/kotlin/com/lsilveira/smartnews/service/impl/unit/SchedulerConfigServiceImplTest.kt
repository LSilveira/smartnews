package com.lsilveira.smartnews.service.impl.unit

import com.lsilveira.smartnews.repository.SchedulerConfigRepository
import com.lsilveira.smartnews.service.impl.SchedulerConfigServiceImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ExtendWith(MockitoExtension::class)
internal class SchedulerConfigServiceImplTest
{
    @InjectMocks
    private lateinit var schedulerConfigService: SchedulerConfigServiceImpl

    @Mock
    private lateinit var schedulerConfigRepository: SchedulerConfigRepository

    @BeforeEach
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun readActiveTasks()
    {
        TODO()
    }
}