package com.jakey.simplefeedingtracker.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "feeding_table")
data class Feeding(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var day: String = "",
    var time: String = "",
    var amount: String = ""
) : Serializable
