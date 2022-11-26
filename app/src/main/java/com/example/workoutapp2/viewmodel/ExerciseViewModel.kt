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

    private val _todoList = MutableLiveData<ArrayList<Exercise>>(arrayListOf())
    val todoList: LiveData<ArrayList<Exercise>>
        get() = _todoList

    private lateinit var currentSelectedDate: String

    init {
        repository.observeWholeList(_wholeList)
    }

    private fun observeToDoByDate(date:String) {
        repository.observeToDoList(_todoList, date)
    }

    fun setDate(date: String) {
        currentSelectedDate = date
        observeToDoByDate(currentSelectedDate)
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
            if (newExercise.name == exercise?.name) return false
        }
        return true
    }
}