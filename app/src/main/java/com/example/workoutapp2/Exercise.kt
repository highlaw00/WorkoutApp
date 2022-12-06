package com.example.workoutapp2

class Exercise(
    val img: Int = R.drawable.ic_basic,
    var name: String? = "",
    var part: String? = "",
    var lastReps: ArrayList<Int>? = null,
    var lastWeights: ArrayList<Double>? = null,
    @JvmField
    var isMainExercise: Boolean? = null,
    @JvmField
    var isDone: Boolean? = null,
)