package com.jakey.simplefeedingtracker.data.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.jakey.simplefeedingtracker.data.model.Feeding
import com.jakey.simplefeedingtracker.data.FeedingDatabase
import com.jakey.simplefeedingtracker.data.repository.FeedingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class FeedingsViewModel(application: Application): AndroidViewModel(application) {

    val readAllFeedings: LiveData<List<Feeding>>
    private val repository: FeedingRepository

    val cal = Calendar.getInstance()
    var day = ""
    var time = ""
    var amount = ""
    var note = ""

    var timeSinceLast: LiveData<Long> = MutableLiveData()

    init {
        val feedingDao = FeedingDatabase.getInstance(application).feedingDao
        repository = FeedingRepository(feedingDao)
        readAllFeedings = repository.getAllFeedings
    }


    fun getLowTimeStamp(): Long? {
        viewModelScope.launch {
            val timestamp = repository.getLowTimestamp()
            timeSinceLast = timestamp
        }
        return timeSinceLast.value
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

    fun deleteFeeding(feeding: Feeding) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFeeding(feeding)
        }
    }

    fun deleteAllFeedings() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFeedings()
        }
    }

}

