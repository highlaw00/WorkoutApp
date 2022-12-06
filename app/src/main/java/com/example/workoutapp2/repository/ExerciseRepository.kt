package com.example.workoutapp2.repository

import androidx.lifecycle.MutableLiveData
import com.example.workoutapp2.DataBaseStorage
import com.example.workoutapp2.Exercise
import com.example.workoutapp2.SetDateCommand
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class ExerciseRepository() {
    private val database = Firebase.database
    private val mainRef = database.getReference(DataBaseStorage.MAIN_DB_STRING)
    private val customRef = database.getReference(DataBaseStorage.CUSTOM_DB_STRING)
    private val dailyRef = database.getReference(DataBaseStorage.DAILY_DB_STRING)

    /** 새로운 커스텀 운동을 데이터베이스에 등록하는 함수 **/
    fun postExerciseToCustom(exercise: Exercise) {
        val key = DataBaseStorage.UNNI_KEY
        customRef.child(key.toString()).child(DataBaseStorage.CUSTOM_WORKOUT_DIRECTORY_STRING).child(exercise.name!!).setValue(exercise)
    }

    /** 오늘 할 운동에 새로운 운동을 등록하는 함수 **/
    fun postExerciseToDaily(exercise: Exercise?, date: String) {
        val key = DataBaseStorage.UNNI_KEY

        // custom exercise 라면 따로 set 정보를 저장할 필요가 없습니다.
        // 단, main exercise 의 경우 set 정보를 custom/uuid/workout_info/exercise.name 에서 가져와 저장합니다.
        val directoryName: String = when(exercise?.isMainExercise) {
            true -> DataBaseStorage.MAIN_WORKOUT_DIRECTORY_STRING
            false -> DataBaseStorage.CUSTOM_WORKOUT_DIRECTORY_STRING
            else -> "Error"
        }

        customRef.child(key.toString()).child(directoryName)
            .child(exercise?.name.toString())
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    exercise?.lastReps = snapshot.getValue(Exercise::class.java)!!.lastReps
                    exercise?.lastWeights = snapshot.getValue(Exercise::class.java)!!.lastWeights
                } else snapshot.ref.setValue(exercise)

                dailyRef.child(key.toString()).child(date).get().addOnSuccessListener {
                    dailyRef.child(key.toString()).child(date).child(exercise?.name.toString())
                        .setValue(exercise)
                }
            }
    }

    /** Repository 시작 시 호출되는 함수, 데이터베이스에 존재하는 모든 운동을 가져오고 리스너를 추가합니다. **/
    fun observeWholeList(list: MutableLiveData<ArrayList<Exercise>>) {

        mainRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList: ArrayList<Exercise>? = list.value
                for (child in snapshot.children) {
                    val item : Exercise? = child.getValue(Exercise::class.java)
                    if (item != null) {
                        tempList?.add(item)
                    }
                }
                tempList?.sortBy { it.name }
                list.value = tempList
            }
            override fun onCancelled(error: DatabaseError) {
            }

        })

        val key = DataBaseStorage.UNNI_KEY
        customRef.child(key.toString()).child(DataBaseStorage.CUSTOM_WORKOUT_DIRECTORY_STRING).addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val tempList: ArrayList<Exercise>? = list.value
                snapshot.getValue(Exercise::class.java)?.let { tempList?.add(it) }
                tempList?.sortBy { it.name }
                list.value = tempList
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val removedExerciseName = snapshot.getValue(Exercise::class.java)?.name
                val tempList: ArrayList<Exercise>? = list.value

                if (tempList != null) {
                    for (exercise in tempList) {
                        if (removedExerciseName == exercise.name) {
                            tempList.remove(exercise)
                            break
                        }
                    }
                }
                list.value = tempList
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    /** 사용자가 날짜를 선택하면 호출되는 함수, 해당 날짜의 운동을 가져오며 리스너를 추가합니다. **/
    fun observeToDoList(list: MutableLiveData<ArrayList<Exercise>>, date: String, command: SetDateCommand) {
        val key = DataBaseStorage.UNNI_KEY

        val childEventListener = object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val tempList: ArrayList<Exercise>? = list.value
                snapshot.getValue(Exercise::class.java)?.let { tempList?.add(it) }
                list.value = tempList
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val tempList: ArrayList<Exercise>? = list.value
                val changedExercise = snapshot.getValue(Exercise::class.java)
                if (tempList != null && changedExercise?.isDone == true) {
                    for (exercise in tempList) {
                        if (changedExercise.name == exercise.name) {
                            exercise.isDone = true
                            break
                        }
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val removedExerciseName = snapshot.getValue(Exercise::class.java)?.name
                val tempList: ArrayList<Exercise>? = list.value

                if (tempList != null) {
                    for (exercise in tempList) {
                        if (removedExerciseName == exercise.name) {
                            tempList.remove(exercise)
                            break
                        }
                    }
                }
                list.value = tempList
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }

        /*
            // 다른 날짜를 선택하는 경우 현재 todoList 를 비워줍니다.
            // 이전에 listener 가 추가되었던 경우를 대비하여 listener 를 삭제해줍니다.
         */
        if (command == SetDateCommand.DIFFERENT) {
            list.value?.clear()
            list.value = arrayListOf()
            dailyRef.child(key.toString()).child(date).removeEventListener(childEventListener)
        }

        dailyRef.child(key.toString()).child(date).addChildEventListener(childEventListener)
    }

    /** 사용자가 추가한 커스텀 운동을 삭제할 때 호출되는 함수 **/
    fun removeFromCustomDB(exercise: Exercise) {
        val key = DataBaseStorage.UNNI_KEY
        customRef.child(key.toString()).child(DataBaseStorage.CUSTOM_WORKOUT_DIRECTORY_STRING).child(exercise.name.toString()).removeValue()
    }

    /** 해야할 운동에 추가한 운동을 삭제할 때 호출되는 함수 **/
    fun removeFromDailyDB(exercise: Exercise, date: String) {
        val key = DataBaseStorage.UNNI_KEY
        dailyRef.child(key.toString()).child(date).child(exercise.name.toString()).removeValue()
    }

    /** 운동이 끝났을 때 호출되는 함수 **/
    fun modifySet(exercise: Exercise, date: String) {
        val key = DataBaseStorage.UNNI_KEY
        val exerciseName = exercise.name.toString()
        val customString = DataBaseStorage.CUSTOM_WORKOUT_DIRECTORY_STRING
        val mainString = DataBaseStorage.MAIN_WORKOUT_DIRECTORY_STRING

        dailyRef.child(key.toString()).child(date).child(exerciseName).child("lastReps").setValue(exercise.lastReps)
        dailyRef.child(key.toString()).child(date).child(exerciseName).child("lastWeights").setValue(exercise.lastWeights)
        dailyRef.child(key.toString()).child(date).child(exerciseName).child("isDone").setValue(true)

        if (exercise.isMainExercise == true) {
            customRef.child(key.toString()).child(mainString).child(exerciseName).child("lastReps").setValue(exercise.lastReps)
            customRef.child(key.toString()).child(mainString).child(exerciseName).child("lastWeights").setValue(exercise.lastWeights)
        } else if (exercise.isMainExercise == false) {
            customRef.child(key.toString()).child(customString).child(exerciseName).child("lastReps").setValue(exercise.lastReps)
            customRef.child(key.toString()).child(customString).child(exerciseName).child("lastWeights").setValue(exercise.lastWeights)
        }
    }

}