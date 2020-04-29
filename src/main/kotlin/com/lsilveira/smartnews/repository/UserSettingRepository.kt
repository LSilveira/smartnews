package com.lsilveira.smartnews.repository

import com.lsilveira.smartnews.model.settings.UserSetting
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserSettingRepository : CrudRepository<UserSetting, Long>
{
    fun findByUsername(username: String) : UserSetting?
}