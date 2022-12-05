package com.example.workoutapp2

object DataBaseEntry {
    const val DAILY_DB_STRING = "dailyDB"
    const val MAIN_DB_STRING = "mainDB"
    const val CUSTOM_DB_STRING = "customDB"
    const val DAILY_RECORD_INFO_STRING = "DAILY RECORD INFO"
    const val CUSTOM_WORKOUT_DIRECTORY_STRING = "USER_CUSTOM"
//    const val IMAGE_ID: Int = R.drawable.ic_basic
//    const val IMAGE_BENCH: Int = R.drawable.ic_bench_press
//    const val IMAGE_CRUNCH: Int = R.drawable.ic_crunch
//    const val IMAGE_ARMS: Int = R.drawable.ic_arms_curl
//    const val IMAGE_PRESS: Int = R.drawable.ic_press
//    const val IMAGE_PUSH: Int = R.drawable.ic_push_up
//    const val IMAGE_SQUAT: Int = R.drawable.ic_squat
//    const val IMAGE_PULL: Int = R.drawable.ic_pull_up
    var UNNI_KEY: String? = null

    fun setUnni(key: String?) {
        UNNI_KEY = key
    }
}