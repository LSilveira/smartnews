package com.lsilveira.smartnews.controller

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Profile("dev")
@Controller
@RequestMapping("/test")
class TestController
{
    @GetMapping
    fun index() : ModelAndView
    {
        val view = ModelAndView("test")

        return view
    }
}