package com.lsilveira.smartnews.security.authentication

import com.lsilveira.smartnews.service.UserSettingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

//@Service
//@Profile("dev")
class DummyUserDetailsService : UserDetailsService
{
    @Autowired
    private lateinit var userSettingService: UserSettingService

    override fun loadUserByUsername(username: String?): UserDetails
    {
        val userSetting = userSettingService.getOrCreateUserSettings(username.takeIf { !it.isNullOrEmpty() }
                ?: throw UsernameNotFoundException("Username cannot be null or empty!"))

        return User(userSetting.username, "{noop}password", AuthorityUtils.NO_AUTHORITIES)
    }
}