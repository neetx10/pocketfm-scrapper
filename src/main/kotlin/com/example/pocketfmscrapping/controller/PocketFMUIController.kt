package com.example.pocketfmscrapping.controller

import com.example.pocketfmscrapping.dto.Show
import com.example.pocketfmscrapping.service.PocketFMDataService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
class PocketFMUIController(val pocketFMDataService: PocketFMDataService) {

    @RequestMapping("/", method = [RequestMethod.GET])
    fun showList() : ModelAndView {
        val mav = ModelAndView("shows");
        mav.addObject("shows", pocketFMDataService.fetchAllShows())
        return mav
    }

    @RequestMapping("/{showId}", method = [RequestMethod.GET])
    fun episodeList(@PathVariable showId: String) : ModelAndView {
        val mav = ModelAndView("episodes");
        mav.addObject("episodes", pocketFMDataService.fetchAllEpisodeByShow(showId))
        return mav
    }
}
