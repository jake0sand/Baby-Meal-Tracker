package com.jakey.simplefeedingtracker.data

import androidx.lifecycle.LiveData

class FeedingRepository(private val feedingDao: FeedingDao) {

    val getAllFeedings: LiveData<List<Feeding>> = feedingDao.getAllFeedings()

    suspend fun addFeeding(feeding: Feeding) {
        feedingDao.insertFeeding(feeding)
    }
}