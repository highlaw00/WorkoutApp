package com.example.workoutapp2

class Exercise(
    val img: Int = R.drawable.ic_basic,
    var name: String? = "",
    var part: String? = "",
    val lastReps: ArrayList<Int> = arrayListOf(0),
    val lastWeights: ArrayList<Double> = arrayListOf(0.0)


){
    override fun toString(): String {
        return "Exercise {name: ${this.name}, part: ${this.part}, img: ${this.img}, lastReps: [${this.lastReps}], lastWeights: [${lastWeights}]}"
    }
}