package com.example.workoutapp2

class Exercise(

    val img: Int = R.drawable.ic_basic,
    var name: String? = "",
    var part: String? = "",
    var lastReps: ArrayList<Int>? = null,
    var lastWeights: ArrayList<Double>? = null,
    @JvmField
    var isMainExercise: Boolean? = null,
    var dailyIndex: Int? = null
){
    override fun toString(): String {
        return "Exercise {name: ${this.name}, part: ${this.part}, img: ${this.img}, lastReps: [${this.lastReps}], lastWeights: [${lastWeights}]}"
    }
    inner class Sets {
        var lastReps: ArrayList<Int>? = null
        var lastWeights: ArrayList<Int>? = null
    }
}