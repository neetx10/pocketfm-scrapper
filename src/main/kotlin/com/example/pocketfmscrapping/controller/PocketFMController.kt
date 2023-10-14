package com.example.pocketfmscrapping.controller

import com.example.pocketfmscrapping.dto.Show
import com.example.pocketfmscrapping.dto.Story
import com.example.pocketfmscrapping.service.PocketFMDataService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pocket-fm")
class PocketFMController(val pocketFMDataService: PocketFMDataService) {
    @RequestMapping(value = ["/all-shows"],method = [RequestMethod.GET])
    fun allShows():List<Show> {
        return pocketFMDataService.fetchAllShows()
    }

    @RequestMapping(value = ["/all-genre"],method = [RequestMethod.GET])
    fun allGenre():Set<String?> {
        return pocketFMDataService.fetchAllGenre()
    }

    @RequestMapping(value = ["/all-shows-by-genre"],method = [RequestMethod.GET])
    fun allShowsByGenre(@RequestParam genre: String):List<Show> {
        return pocketFMDataService.fetchAllShowsByGenre(genre)
    }

    @RequestMapping(value = ["/all-episodes-by-show"],method = [RequestMethod.GET])
    fun allEpisodesByShow(@RequestParam show: String):List<Story> {
        return pocketFMDataService.fetchAllEpisodeByShow(show)
    }
}