package com.jakey.simplefeedingtracker.data.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.jakey.simplefeedingtracker.data.model.Feeding
import com.jakey.simplefeedingtracker.data.FeedingDatabase
import com.jakey.simplefeedingtracker.data.repository.FeedingRepository
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

    fun updateFeeding(feeding: Feeding) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFeeding(feeding)
        }
    }

}

