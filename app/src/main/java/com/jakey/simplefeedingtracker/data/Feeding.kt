package com.jakey.simplefeedingtracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feeding_table")
data class Feeding(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var day: String = "",
    var time: String = "",
    var amount: String = ""
)
