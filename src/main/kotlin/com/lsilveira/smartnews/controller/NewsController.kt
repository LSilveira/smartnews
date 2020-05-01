package com.lsilveira.smartnews.controller

import com.lsilveira.smartnews.form.NewsForm
import com.lsilveira.smartnews.model.aggregator.AggregatorContext
import com.lsilveira.smartnews.model.aggregator.news.NewsAggregator
import com.lsilveira.smartnews.service.UserSettingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/news")
class NewsController
{
    @Autowired
    private lateinit var userSettingService: UserSettingService

    @Autowired
    private lateinit var rssAggregator: NewsAggregator

    @GetMapping
    fun index() : ModelAndView
    {
        val view = ModelAndView("news/home")
        view.addObject("newsForm", NewsForm())

        return view
    }

    @PostMapping("/get-data")
    fun getData(@ModelAttribute("newsForm") newsForm: NewsForm) : ModelAndView
    {
        val view = ModelAndView("news/home")
//        view.addObject("newsForm", NewsForm())

        val context = AggregatorContext(newsForm.aggregatorId)
        val data = rssAggregator.aggregate(context)
        view.addObject("rssNews", data)

        return view
    }

    @ModelAttribute
    fun addAttributes(model: Model)
    {
        model.addAttribute("aggregators", userSettingService.getAggregators(SecurityContextHolder.getContext().authentication.name))
    }
}