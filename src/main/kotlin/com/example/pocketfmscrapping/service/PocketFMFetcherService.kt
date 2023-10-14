package com.example.pocketfmscrapping.service

import com.example.pocketfmscrapping.dto.Show
import com.example.pocketfmscrapping.entity.ShowEntity
import com.example.pocketfmscrapping.entity.StoryEntity
import com.example.pocketfmscrapping.repository.ShowRepository
import com.example.pocketfmscrapping.repository.StoryRepository
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class PocketFMFetcherService(val pocketFMService: PocketFMService, val showRepository: ShowRepository, val storyRepository: StoryRepository) {

    private val taskExecutor: SimpleAsyncTaskExecutor = SimpleAsyncTaskExecutor()

    @Scheduled(cron = "0 35 14 ? * *")
    fun fetchAllEpisodesForAllShows() {
        taskExecutor.concurrencyLimit = 5
        showRepository.findAll().forEach {
            it.id?.let {it1 ->
                taskExecutor.execute { fetchAllEpisodes(it1, 0) }
            }
        }
    }

    private fun fetchAllEpisodes(show: String, currPtr: Int) {
        val stories = pocketFMService.fetchEpisodes(show,currPtr)
        if(stories?.isEmpty() == false && stories.first().stories?.isEmpty() == false) {
            val typeRef : TypeReference<List<StoryEntity>> = object : TypeReference<List<StoryEntity>>() {}
            storyRepository.saveAll(ObjectMapper().convertValue(stories.first().stories,typeRef))
            println("${stories.first().show_title} :: total episodes = ${stories.first().episodes_count} :: current top episode = ${stories.first().stories?.first()?.story_title}")
        }
        while (stories?.first()?.stories?.count() == 10) {
            fetchAllEpisodes(show,currPtr + 10)
            return
        }
    }

    @Scheduled(cron = "0 30 14 ? * *")
    fun fetchAllShows() {
        val listTabs = pocketFMService.fetchFeedTabs()
        listTabs.let {
            it?.forEach {
                val listFeeds = it.apiType?.let { it1 -> pocketFMService.fetchShowsByTabs(it1) }
                listFeeds.let {
                    it?.forEach{
                        it.entities?.forEach{
                            if(it.type.equals("show")) {
                                val show: Show = ObjectMapper().convertValue(it.value, Show::class.java)
                                saveStory(show)
                                println("Saving : ${show.show_title}")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun saveStory(show: Show) {
        showRepository.save(ObjectMapper().convertValue(show, ShowEntity::class.java))
    }
}