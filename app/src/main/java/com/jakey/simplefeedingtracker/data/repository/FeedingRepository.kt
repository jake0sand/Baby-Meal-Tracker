package com.jakey.simplefeedingtracker.data.repository

import androidx.lifecycle.LiveData
import com.jakey.simplefeedingtracker.data.FeedingDao
import com.jakey.simplefeedingtracker.data.model.Feeding

class FeedingRepository(private val feedingDao: FeedingDao) {

    val getAllFeedings: LiveData<List<Feeding>> = feedingDao.getAllFeedings()

    suspend fun addFeeding(feeding: Feeding) {
        feedingDao.insertFeeding(feeding)
    }

    suspend fun updateFeeding(feeding: Feeding) {
        feedingDao.updateFeeding(feeding)
    }
}