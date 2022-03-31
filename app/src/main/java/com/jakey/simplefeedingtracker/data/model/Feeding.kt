package com.jakey.simplefeedingtracker.data.model

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//Todo timestamp stuff
interface DataPoint : Serializable //pass just ID, no serialize

@Entity(tableName = "feeding_table")
data class Feeding(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var day: String = "",
    var timeStamp: Long = 0,
    var time: String = "",
    var amount: String = "",
    var note: String = ""
) : DataPoint

data class Header(val day: String?, val amount: String) : DataPoint

data class StickyHeader(var timeSinceLast: String?): DataPoint