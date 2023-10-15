package com.example.pocketfmscrapping.controller

import com.example.pocketfmscrapping.service.PocketFMDataService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/")
class PocketFMUIController(val pocketFMDataService: PocketFMDataService) {

    @RequestMapping(value = ["/","/shows"],method = [RequestMethod.GET])
    fun showList() : ModelAndView {
        val mav = ModelAndView("showList");
        mav.addObject("shows", pocketFMDataService.fetchAllShows())
        return mav
    }

    @RequestMapping("/episodes",method = [RequestMethod.GET])
    fun episodeList(@RequestParam showId: String) : ModelAndView {
        val mav = ModelAndView("episodeList");
        mav.addObject("episodes", pocketFMDataService.fetchAllEpisodeByShow(showId))
        return mav
    }
}
