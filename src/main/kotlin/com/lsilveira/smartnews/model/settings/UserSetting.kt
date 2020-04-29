package com.lsilveira.smartnews.model.settings

import javax.persistence.*

/**
 * User setting
 */
@Entity
@Table(name = "SN_USER_SETTING")
data class UserSetting
(
        val username: String,

        val password: String,

        @OneToMany(mappedBy = "userSetting", cascade = [CascadeType.ALL], orphanRemoval = true)
        val aggregatorMappings: MutableList<AggregatorMapping> = mutableListOf()
)
{
        constructor
                (
                        id: Long,

                        username: String,

                        password: String,

                        aggregatorMappings: MutableList<AggregatorMapping> = mutableListOf()
                ) : this(username, password, aggregatorMappings)

    @Id
    @GeneratedValue
    val id: Long = 0
}