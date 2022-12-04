package com.example.workoutapp2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workoutapp2.Exercise
import com.example.workoutapp2.SetDateCommand
import com.example.workoutapp2.repository.ExerciseRepository
import java.util.*
import kotlin.collections.ArrayList

class ExerciseViewModel: ViewModel() {

    private val repository: ExerciseRepository = ExerciseRepository()

    private val _wholeList = MutableLiveData<ArrayList<Exercise>>(arrayListOf())
    val wholeList: LiveData<ArrayList<Exercise>>
        get() = _wholeList

    private val _todoList = MutableLiveData<ArrayList<Exercise>>(arrayListOf())
    val todoList: LiveData<ArrayList<Exercise>>
        get() = _todoList

    private var currentSelectedDate: String? = null

    init {
        repository.observeWholeList(_wholeList)
    }

    private fun observeToDoByDate(date: String, command: SetDateCommand) = repository.observeToDoList(_todoList, date, command)

    fun setDate(date: String) {

        // 처음 실행한 경우거나 Entry에서 선택한 날짜가 이전 날짜와 다른 경우에만 observe합니다.
        var command = if (currentSelectedDate == null) SetDateCommand.INIT else SetDateCommand.DIFFERENT
        currentSelectedDate = date
        observeToDoByDate(currentSelectedDate!!, command)

    }
    fun getDate(): String? = currentSelectedDate

    fun addToCustom(exercise: Exercise) = repository.postToCustom(exercise)

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
        for (exercise in _wholeList.value!!) {
            if (newExercise.name == exercise.name) return false
        }
        return true
    }

    fun addToDaily(exercise: Exercise?) = currentSelectedDate?.let { repository.postToDailyDB(exercise, it) }
    fun updateSetByPosition(position: Int, reps: Int, weight: Double) {
        val viewModelExercise = _todoList.value?.get(position)
        if (viewModelExercise?.lastReps == null && viewModelExercise?.lastWeights == null) {
            viewModelExercise?.lastReps = arrayListOf(reps)
            viewModelExercise?.lastWeights = arrayListOf(weight)
        } else {
            viewModelExercise.lastReps?.add(reps)
            viewModelExercise.lastWeights?.add(weight)
        }
    }

    fun deleteLastSet(position: Int) {
        val viewModelExercise = _todoList.value?.get(position)
        val lastIdx = viewModelExercise?.lastReps?.lastIndex
        // null을 가르키고 있는 경우 lastIdx마저 null이 된다.
        // size가 1 이상인 배열을 가르키는 경우 lastIdx는 마지막 인덱스를 가리킨다.
        // delete로 들어올 때는 exercise.lastReps가 null일 수 없다.
        if (lastIdx != -1) {
            viewModelExercise?.lastReps?.removeAt(lastIdx!!)
            viewModelExercise?.lastWeights?.removeAt(lastIdx!!)
        }
        if (viewModelExercise?.lastReps?.size == 0 && viewModelExercise.lastWeights?.size == 0) {
            viewModelExercise.lastReps = null
            viewModelExercise.lastWeights = null
        }
    }

    fun removeFromCustom(exercise: Exercise?) {
        if (exercise != null) {
            repository.removeFromCustomDB(exercise)
        }
    }

    fun removeFromDaily(position: Int) {
        val exercise = _todoList.value?.get(position)
        if (exercise != null) {
            repository.removeFromDailyDB(exercise, currentSelectedDate.toString())
        }
    }

    fun checkStartValidation(): Boolean {
        for (exercise in todoList.value?.toList()!!) {
            if (exercise.lastReps == null && exercise.lastWeights == null) return false
        }
        return true
    }

    fun swapExercise(fromPos: Int, toPos: Int) {
        val tempList: ArrayList<Exercise>? = _todoList.value
        if (tempList != null) {
            Collections.swap(tempList, fromPos, toPos)
        }
        _todoList.value = tempList!!
    }
}