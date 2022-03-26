package com.jakey.simplefeedingtracker.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jakey.simplefeedingtracker.data.model.Feeding

@Dao
interface FeedingDao {

    @Insert
    suspend fun insertFeeding(feeding: Feeding)

    @Update
    suspend fun updateFeeding(feeding: Feeding)

    @Delete
    suspend fun deleteFeeding(feeding: Feeding)

    @Query("SELECT min(timeStamp) FROM feeding_table")
    fun getLowTimestamp(): LiveData<Long>

    @Query("DELETE FROM feeding_table")
    suspend fun deleteAllFeedings()

    @Query("SELECT * FROM feeding_table WHERE id = :key")
    fun getFeeding(key: Long) : LiveData<Feeding>

    @Query("SELECT * FROM feeding_table ORDER BY timeStamp DESC")
    fun getAllFeedings(): LiveData<List<Feeding>>

}