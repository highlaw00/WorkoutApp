package com.example.workoutapp2

object DataBaseEntry {
    const val DAILY_DB_STRING = "dailyDB"
    const val MAIN_DB_STRING = "mainDB"
    const val CUSTOM_DB_STRING = "customDB"
    const val DAILY_RECORD_INFO_STRING = "DAILY RECORD INFO"
    const val CUSTOM_WORKOUT_DIRECTORY_STRING = "USER_CUSTOM"
    var UNNI_KEY: String? = null

    fun setUnni(key: String?) {
        UNNI_KEY = key
    }
}