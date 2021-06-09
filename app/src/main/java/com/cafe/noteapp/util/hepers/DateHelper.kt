package com.cafe.noteapp.util.hepers


object DateHelper {

    fun getCurrentDateInMilli(): Long =
            System.currentTimeMillis()

    fun calculateTimeDifference(desTime: Long) = desTime - getCurrentDateInMilli()


}