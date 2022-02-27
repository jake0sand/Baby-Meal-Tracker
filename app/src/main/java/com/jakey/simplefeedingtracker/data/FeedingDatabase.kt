package com.jakey.simplefeedingtracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jakey.simplefeedingtracker.data.model.Feeding

@Database(entities = [Feeding::class], version = 2, exportSchema = false)
abstract class FeedingDatabase : RoomDatabase() {
    abstract val feedingDao: FeedingDao

    companion object {
        @Volatile
        private var INSTANCE: FeedingDatabase? = null

        fun getInstance(context: Context): FeedingDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FeedingDatabase::class.java,
                        "feedings_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}