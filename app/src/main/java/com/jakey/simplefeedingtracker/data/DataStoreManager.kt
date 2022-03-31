package com.jakey.simplefeedingtracker.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class DataStoreManager(val context: Context) {

    suspend fun save(value: String) {

        context.dataStore.edit { settings ->
            settings[PHONE_NUMBER] = value
        }
    }

    suspend fun readPhoneNumber(): String {
        val preferences = context.dataStore.data.first()
        return preferences[PHONE_NUMBER].toString()
    }

    companion object {
        private const val DATASTORE_NAME = "settings"

        private val PHONE_NUMBER = stringPreferencesKey("phone_number")

        private val Context.dataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}