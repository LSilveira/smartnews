package com.lsilveira.smartnews.controller.ajax

import com.lsilveira.smartnews.service.NewsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
@RequestMapping("/ajax/news")
class NewsAjaxController
{
    @Autowired
    private lateinit var newsService: NewsService

    @GetMapping("/data/{aggregatorMappingId}")
    fun getNews(@PathVariable("aggregatorMappingId") aggregatorMappingId: Long) : ModelAndView
    {

        //TODO validate aggregatorMappingId

        val news = newsService.getData(aggregatorMappingId)

        val view = if (news.isEmpty())
            ModelAndView("news/dataTable :: #emptyData")
        else
            ModelAndView("news/dataTable :: .table")

        view.addObject("articles", news)

        return view
    }
}