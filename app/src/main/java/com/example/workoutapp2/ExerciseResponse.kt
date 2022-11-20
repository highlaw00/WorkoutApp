package com.example.workoutapp2

data class ExerciseResponse(
    var workouts: List<Exercise>? = null,
    var exception: Exception? = null
)