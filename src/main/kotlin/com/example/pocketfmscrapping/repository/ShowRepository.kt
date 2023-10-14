package com.example.pocketfmscrapping.repository

import com.example.pocketfmscrapping.entity.ShowEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ShowRepository : MongoRepository<ShowEntity,String> {
    fun findAllByGenre(genre: String?): List<ShowEntity>?
}