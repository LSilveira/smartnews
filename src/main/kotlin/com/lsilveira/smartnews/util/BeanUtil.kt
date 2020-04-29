package com.lsilveira.smartnews.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service;

@Component
class BeanUtil : ApplicationContextAware
{
    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext)
    {
        context = applicationContext
    }

    companion object
    {
        private var context: ApplicationContext? = null

        fun <T> getBean(beanClass: Class<T>): T
        {
            return context!!.getBean(beanClass)
        }

        fun getBean(beanClass: String): Any
        {
            return context!!.getBean(beanClass)
        }
    }
}