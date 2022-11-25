package com.example.workoutapp2.viewmodel

import android.util.Log
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

    fun getListByPart(part: String): MutableList<Exercise>? {
        val filteredByPart: MutableList<Exercise>? = when (part) {
            "All" -> _wholeList.value?.toMutableList()
            else -> _wholeList.value?.filter {
                it.part == part
            }?.toMutableList()
        }
        return filteredByPart
    }

    fun isValid(newExercise: Exercise): Boolean {
        Log.d("debugshow", "wholeList: ${_wholeList.value ?: "empty"}")
        for (exercise in _wholeList.value!!) {
            Log.d("debugshow", "exercise: ${exercise.name}")
            if (newExercise.name == exercise?.name)
                Log.d("debugshow", "new: ${newExercise.name}, exercise: ${exercise.name}")
                return false
        }
        return true
    }

//    fun getWholeListByPart(part: String) {
//        val filteredByPart:LiveData<ArrayList<Exercise>> = when (part) {
//            "All" -> _wholeList
//            else -> _wholeList.value.filter {
//
//            }
//        }
//    }

}