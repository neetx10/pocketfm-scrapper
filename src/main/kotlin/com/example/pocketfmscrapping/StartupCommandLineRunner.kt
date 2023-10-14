package com.example.pocketfmscrapping

import com.example.pocketfmscrapping.service.PocketFMFetcherService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class StartupCommandLineRunner(
        val pocketFMFetcherService: PocketFMFetcherService,
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        println("Start")
        pocketFMFetcherService.fetchAllShows()
        pocketFMFetcherService.fetchAllEpisodesForAllShows()
        println("End")
    }
}