package com.jakey.simplefeedingtracker.presentation

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.*
import com.jakey.simplefeedingtracker.data.model.Feeding
import com.jakey.simplefeedingtracker.data.FeedingDatabase
import com.jakey.simplefeedingtracker.data.repository.FeedingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class SharedViewModel(application: Application): AndroidViewModel(application) {

    val readAllFeedings: LiveData<List<Feeding>>
    private val repository: FeedingRepository

    var cal = Calendar.getInstance()

    var day = ""
    var time = ""
    var amount = ""
    var note = ""


    private val _phoneNumber = MutableLiveData<String>("")
    val phoneNumber: LiveData<String> = _phoneNumber

    fun getPhoneNumber(number: String?): String? {
        _phoneNumber.value = number ?: ""
        return number
    }
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

    fun insertFeeding(day: String?, time: String?, amount: String?, note: String?) {



        if (inputCheck(day.toString(), time.toString(), amount.toString())) {
            val feeding = Feeding(
                day = day.toString(),
                time = time.toString(),
                amount = amount.toString(),
                note = note.toString(),
                timeStamp = cal.timeInMillis
            )

            addFeeding(feeding)



        }
    }
    fun inputCheck(day: String, time: String, amount: String): Boolean {
        return !(TextUtils.isEmpty(day) || TextUtils.isEmpty(time) || TextUtils.isEmpty(amount))
    }

}

