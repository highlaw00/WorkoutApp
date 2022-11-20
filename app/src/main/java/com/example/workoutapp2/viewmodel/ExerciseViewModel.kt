package com.example.workoutapp2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workoutapp2.Exercise
import com.example.workoutapp2.repository.ExerciseRepository

class ExerciseViewModel: ViewModel() {

    private val repository: ExerciseRepository = ExerciseRepository()

    private val _mainList = MutableLiveData<List<Exercise>>()
    val mainList: LiveData<List<Exercise>>
            get() = _mainList

    private val _customList = MutableLiveData<List<Exercise>>()
    val customList: LiveData<List<Exercise>>
        get() = _customList


    init {
        repository.observeMainList(_mainList)
        repository.observeToDoList(_customList)
    }

    fun getMainExercises(): List<Exercise> {
        return _mainList.value?.toList() ?: listOf()
    }

    fun addToCustom(exercise: Exercise) {
//        _customList.value = _customList.value?.plus(listOf(exercise))
        repository.postToCustom(exercise)
    }
}