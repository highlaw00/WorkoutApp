package com.example.workoutapp2

class Exercise(
    val img: Int = R.drawable.ic_basic,
    val name: String? = null,
    val part: String? = null,
    val lastReps: ArrayList<Int> = arrayListOf(0),
    val lastWeights: ArrayList<Double> = arrayListOf(0.0)
)