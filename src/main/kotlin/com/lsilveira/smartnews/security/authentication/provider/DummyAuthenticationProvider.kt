package com.lsilveira.smartnews.security.authentication.provider

import com.lsilveira.smartnews.service.UserSettingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class DummyAuthenticationProvider : AuthenticationProvider
{
    @Autowired
    private lateinit var userSettingService: UserSettingService

    override fun authenticate(authentication: Authentication?): Authentication
    {
        val username = authentication?.name
                ?:throw BadCredentialsException("Authentication failed")

        val userSetting = userSettingService.getOrCreateUserSettings(username)
        return UsernamePasswordAuthenticationToken(userSetting.username, "", Collections.emptyList())
    }

    override fun supports(aClass: Class<*>?): Boolean
    {
        return aClass?.let { clazz -> clazz == UsernamePasswordAuthenticationToken::class.java }
                ?:false
    }
}