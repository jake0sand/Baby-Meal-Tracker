package com.jakey.simplefeedingtracker.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FeedingDao {

    @Insert
    suspend fun insertFeeding(feeding: Feeding)

    @Update
    suspend fun updateFeeding(feeding: Feeding)

    @Delete
    suspend fun deleteFeeding(feeding: Feeding)

    @Query("SELECT * FROM feeding_table WHERE id = :key")
    fun getFeeding(key: Long) : LiveData<Feeding>

    @Query("SELECT * FROM feeding_table ORDER BY id DESC")
    fun getAllFeedings(): LiveData<List<Feeding>>

}