package com.lsilveira.smartnews.security.authentication

import com.lsilveira.smartnews.model.settings.UserSetting
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(val userSetting: UserSetting) : UserDetails
{
    override fun getAuthorities(): MutableCollection<out GrantedAuthority>
    {
        return mutableListOf<GrantedAuthority>()
    }

    override fun isEnabled(): Boolean
    {
        return true
    }

    override fun getUsername(): String
    {
        return userSetting.username
    }

    override fun isCredentialsNonExpired(): Boolean
    {
        return true
    }

    override fun getPassword(): String
    {
        return userSetting.password
    }

    override fun isAccountNonExpired(): Boolean
    {
        return true
    }

    override fun isAccountNonLocked(): Boolean
    {
        return true
    }
}