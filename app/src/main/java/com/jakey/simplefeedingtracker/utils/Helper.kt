package com.jakey.simplefeedingtracker.utils

import java.text.SimpleDateFormat

object Helper {
    private val sdfDay = SimpleDateFormat("EEEE, MMMM dd YYYY")
    private val sdfTime = SimpleDateFormat("h:mm a")

    fun convertDay(timeStamp: Long): String? = sdfDay.format(timeStamp)
    fun convertTime(timeStamp: Long): String? = sdfTime.format(timeStamp)

    fun convertDayFromString(timeStamp: String?): String? = sdfDay.format(timeStamp)
    fun convertTimeFromString(timeStamp: String?): String? = sdfDay.format(timeStamp)

}