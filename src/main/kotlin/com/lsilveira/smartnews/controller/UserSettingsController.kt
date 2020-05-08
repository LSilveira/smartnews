package com.lsilveira.smartnews.controller

import com.lsilveira.smartnews.form.AggregatorForm
import com.lsilveira.smartnews.service.UserSettingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import javax.validation.Valid

@Controller
@RequestMapping("/user-setting")
class UserSettingsController
{
    @Autowired
    private lateinit var userSettingService: UserSettingService

    @GetMapping
    fun index() : ModelAndView
    {
        val view = ModelAndView("settings/home")
        view.addObject("aggregatorForm", AggregatorForm())

        return view
    }

    @PostMapping("/rss")
    fun createRSS(@Valid @ModelAttribute("aggregatorForm") aggregatorForm: AggregatorForm,
                  bindingResult: BindingResult) : ModelAndView
    {
        val view: ModelAndView

        if (bindingResult.hasErrors())
        {
            view = ModelAndView("settings/home")
        }
        else
        {
            view = ModelAndView("redirect:/user-setting")
            val userName = SecurityContextHolder.getContext().authentication.name

            userSettingService.addAggregator(userName, aggregatorForm.aggregatorType, aggregatorForm.url,
                    aggregatorForm.topic)
        }

        return view
    }

    @ModelAttribute
    fun addAttributes(model: Model)
    {
        model.addAttribute("aggregators", userSettingService.getAggregators(SecurityContextHolder.getContext().authentication.name))
    }
}