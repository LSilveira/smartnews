package com.lsilveira.smartnews.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter()
{
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity)
    {
        http
                .authorizeRequests()
//                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .csrf().disable().cors() //TODO remove this to do it properly
                .and()
                .logout()
                .permitAll()
    }

//    @Bean
//    override fun userDetailsService(): UserDetailsService? {
//        return DummyUserDetailsService()
//    }

//    @Bean
//    fun passwordEncoder(): BCryptPasswordEncoder? {
//        return BCryptPasswordEncoder()
//    }
//
//    @Throws(java.lang.Exception::class)
//    override fun configure(auth: AuthenticationManagerBuilder) {
//        auth.userDetailsService(DummyUserDetailsService()).passwordEncoder(passwordEncoder())
//    }
}