package com.example.pocketfmscrapping

import com.example.pocketfmscrapping.dto.Show
import com.example.pocketfmscrapping.entity.ShowEntity
import com.example.pocketfmscrapping.entity.StoryEntity
import com.example.pocketfmscrapping.repository.ShowRepository
import com.example.pocketfmscrapping.repository.StoryRepository
import com.example.pocketfmscrapping.service.PocketFMFetcherService
import com.example.pocketfmscrapping.service.PocketFMService
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.CommandLineRunner
import org.springframework.core.task.SimpleAsyncTaskExecutor
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