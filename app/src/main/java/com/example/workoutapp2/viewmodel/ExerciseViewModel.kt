package com.example.workoutapp2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workoutapp2.Exercise
import com.example.workoutapp2.repository.ExerciseRepository

class ExerciseViewModel: ViewModel() {

    private val repository: ExerciseRepository = ExerciseRepository()

    private val _wholeList = MutableLiveData<ArrayList<Exercise>>(arrayListOf())
    val wholeList: LiveData<ArrayList<Exercise>>
        get() = _wholeList
    private var listOfWholeList: ArrayList<Exercise>? = null

    init {
        repository.observeWholeList(_wholeList)
    }

    private fun checkValidation(exercise: Exercise): Boolean {
        for (element in _wholeList.value!!) {
            if (element.name == exercise.name) return false
        }
        return true
    }

    fun addToCustom(exercise: Exercise) {
        repository.postToCustom(exercise)
    }


}