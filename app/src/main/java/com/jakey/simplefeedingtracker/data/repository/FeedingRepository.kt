package com.jakey.simplefeedingtracker.data.repository

import androidx.lifecycle.LiveData
import com.jakey.simplefeedingtracker.data.FeedingDao
import com.jakey.simplefeedingtracker.data.model.Feeding

class FeedingRepository(private val feedingDao: FeedingDao) {

    val getAllFeedings: LiveData<List<Feeding>> = feedingDao.getAllFeedings()

    suspend fun addFeeding(feeding: Feeding) {
        feedingDao.insertFeeding(feeding)
    }

    fun getLowTimestamp(): LiveData<Long> {
        return feedingDao.getLowTimestamp()
    }

    suspend fun updateFeeding(feeding: Feeding) {
        feedingDao.updateFeeding(feeding)
    }

    suspend fun deleteFeeding(feeding: Feeding) {
        feedingDao.deleteFeeding(feeding)
    }

    suspend fun deleteAllFeedings() {
        feedingDao.deleteAllFeedings()
    }
}