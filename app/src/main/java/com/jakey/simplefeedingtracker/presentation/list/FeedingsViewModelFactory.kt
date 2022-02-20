package com.jakey.simplefeedingtracker.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jakey.simplefeedingtracker.data.FeedingDao
import java.lang.IllegalArgumentException
//
//class FeedingsViewModelFactory(private val dao: FeedingDao): ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(FeedingsViewModel::class.java)) {
//            return FeedingsViewModel(dao) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel")
//    }
//}