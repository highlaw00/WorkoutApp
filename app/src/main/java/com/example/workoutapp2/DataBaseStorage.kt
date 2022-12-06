package com.example.workoutapp2

object DataBaseStorage {
    const val DAILY_DB_STRING = "daily"
    const val MAIN_DB_STRING = "main"
    const val CUSTOM_DB_STRING = "custom"
    const val CUSTOM_WORKOUT_DIRECTORY_STRING = "custom workout info"
    const val MAIN_WORKOUT_DIRECTORY_STRING = "main workout info"
    var UNNI_KEY: String? = null

    fun setUnni(key: String?) {
        UNNI_KEY = key
    }
}