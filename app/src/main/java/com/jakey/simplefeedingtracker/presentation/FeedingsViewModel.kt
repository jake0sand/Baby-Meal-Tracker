package com.jakey.simplefeedingtracker.presentation

import android.app.Application
import androidx.lifecycle.*
import com.jakey.simplefeedingtracker.data.Feeding
import com.jakey.simplefeedingtracker.data.FeedingDao
import com.jakey.simplefeedingtracker.data.FeedingDatabase
import com.jakey.simplefeedingtracker.data.FeedingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedingsViewModel(application: Application): AndroidViewModel(application) {

    val readAllFeedings: LiveData<List<Feeding>>
    private val repository: FeedingRepository

    init {
        val feedingDao = FeedingDatabase.getInstance(application).feedingDao
        repository = FeedingRepository(feedingDao)
        readAllFeedings = repository.getAllFeedings
    }



    fun addFeeding(feeding: Feeding) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFeeding(feeding)
        }
    }
}

