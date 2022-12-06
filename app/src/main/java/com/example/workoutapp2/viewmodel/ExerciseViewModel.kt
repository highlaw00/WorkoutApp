package com.example.workoutapp2.viewmodel

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

    /**
     * 사용자가 날짜를 선택하면 호출되는 함수
     * 사용자가 선택한 날짜에 따라 _todoList 를 observe 합니다. **/
    private fun observeToDoByDate(date: String, command: SetDateCommand) = repository.observeToDoList(_todoList, date, command)

    /** 사용자가 날짜를 선택하면 호출되는 함수 **/
    fun setDate(date: String) {

        // 처음 실행한 경우거나 Entry에서 선택한 날짜가 이전 날짜와 다른 경우에만 observe합니다.
        var command = if (currentSelectedDate == null) SetDateCommand.INIT else SetDateCommand.DIFFERENT
        currentSelectedDate = date
        observeToDoByDate(currentSelectedDate!!, command)

    }

    fun getDate(): String? = currentSelectedDate

    /** 사용자가 커스텀 운동을 추가하면 호출되는 함수 **/
    fun addToCustom(exercise: Exercise) = repository.postExerciseToCustom(exercise)

    /** 운동 추가 화면에서 각 부위 별 Fragment 에서 호출하는 함수 **/
    fun getListByPart(part: String): MutableList<Exercise>? {
        val filteredByPart: MutableList<Exercise>? = when (part) {
            "All" -> _wholeList.value?.toMutableList()
            else -> _wholeList.value?.filter {
                it.part == part
            }?.toMutableList()
        }
        return filteredByPart
    }

    /** 새로운 운동을 추가할 때 중복된 운동은 아닌지 확인합니다. **/
    fun isValid(newExercise: Exercise): Boolean {
        for (exercise in _wholeList.value!!) {
            if (newExercise.name == exercise.name) return false
        }
        return true
    }

    /** 운동 리스트에서 운동을 오늘 할 운동으로 추가할 때 호출되는 함수 **/
    fun addToDaily(exercise: Exercise?) = currentSelectedDate?.let { repository.postExerciseToDaily(exercise, it) }

    /** 사용자가 운동을 시작하기 전 세트를 추가할 때 호출되는 함수 **/
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

    /** 사용자가 운동을 시작하기 전 세트를 삭제할 때 호출되는 함수 **/
    fun deleteLastSet(position: Int) {
        val viewModelExercise = _todoList.value?.get(position)
        val lastIdx = viewModelExercise?.lastReps?.lastIndex
        if (lastIdx != -1) {
            viewModelExercise?.lastReps?.removeAt(lastIdx!!)
            viewModelExercise?.lastWeights?.removeAt(lastIdx!!)
        }
        if (viewModelExercise?.lastReps?.size == 0 && viewModelExercise.lastWeights?.size == 0) {
            viewModelExercise.lastReps = null
            viewModelExercise.lastWeights = null
        }
    }

    /** 커스텀 운동을 전체 운동 리스트에서 삭제할 때 호출되는 함수 **/
    fun removeFromCustom(exercise: Exercise?) {
        if (exercise != null) {
            repository.removeFromCustomDB(exercise)
        }
    }

    /** 운동을 해야 할 운동 리스트에서 삭제할 때 호출되는 함수 **/
    fun removeFromDaily(position: Int) {
        val exercise = _todoList.value?.get(position)
        if (exercise != null) {
            repository.removeFromDailyDB(exercise, currentSelectedDate.toString())
        }
    }

    /** 운동 시작 전, 할 운동이 비었거나 세트가 추가되지 않았는지 확인하는 함수 **/
    fun checkStartValidation(): Boolean {
        if (todoList.value?.isEmpty() == true) return false

        for (exercise in todoList.value?.toList()!!) {
            if (exercise.lastReps == null && exercise.lastWeights == null) return false
        }

        return true
    }

    /** 해야할 운동의 RecyclerView 를 Drag & Drop 으로 자리를 바꾸면 호출되는 함수 **/
    fun swapExercise(fromPos: Int, toPos: Int) {
        val tempList: ArrayList<Exercise>? = _todoList.value
        if (tempList != null) {
            Collections.swap(tempList, fromPos, toPos)
        }
        _todoList.value = tempList!!
    }

    /** 운동을 모두 마치면 호출되는 함수 **/
    fun updateSetByExercise(exercise: Exercise?) {
        if (exercise != null) {
            repository.modifySet(exercise, currentSelectedDate.toString())
        }
    }
}