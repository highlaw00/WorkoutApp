package com.example.workoutapp2.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.workoutapp2.DataBaseEntry
import com.example.workoutapp2.Exercise
import com.example.workoutapp2.SetDateCommand
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ExerciseRepository() {
    private val database = Firebase.database
    private val mainRef = database.getReference("main")
    private val customRef = database.getReference("custom")
    private val dailyRef = database.getReference("daily")

    fun postToCustom(exercise: Exercise) {
        val key = DataBaseEntry.UNNI_KEY
        customRef.child(key.toString()).child("custom workout info").child(exercise.name!!).setValue(exercise)
    }

    fun postToDailyDB(exercise: Exercise?, date: String) {
        val key = DataBaseEntry.UNNI_KEY
        // custom exercise 라면 따로 set 정보를 저장할 필요가 없습니다.
        // 단, main exercise 의 경우 set 정보를 custom/uuid/workout_info/exercise.name 에서 가져와 저장합니다.
        if (exercise?.isMainExercise == false) {
            dailyRef.child(key.toString()).child(date).child(exercise.name.toString())
                .setValue(exercise)
        } else if (exercise?.isMainExercise == true) {
            customRef.child(key.toString()).child("main workout info").child(exercise.name.toString())
                .get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        // 이전 값이 존재한다면...
                        // 전에 운동한것이기 때문에 이전 세트 정보를 받아옵니다.
                        // 받아온 정보로 수정하고 daily ref에 저장합니다.
                        exercise.lastReps = snapshot.getValue(Exercise::class.java)!!.lastReps
                        exercise.lastWeights = snapshot.getValue(Exercise::class.java)!!.lastWeights
                    }else snapshot.ref.setValue(exercise)
                    dailyRef.child(key.toString()).child(date).child(exercise.name.toString())
                        .setValue(exercise)
                }
        }
    }

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

        val key = DataBaseEntry.UNNI_KEY
        customRef.child(key.toString()).child("custom workout info").addChildEventListener(object: ChildEventListener {
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

    fun observeToDoList(list: MutableLiveData<ArrayList<Exercise>>, date: String, command: SetDateCommand) {
        val key = DataBaseEntry.UNNI_KEY

        if (command == SetDateCommand.DIFFERENT) {
            list.value?.clear()
            list.value = arrayListOf()
            dailyRef.removeEventListener(object: ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }

        dailyRef.child(key.toString()).child(date).addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val tempList: ArrayList<Exercise>? = list.value
                snapshot.getValue(Exercise::class.java)?.let { tempList?.add(it) }
                list.value = tempList
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    fun removeFromCustomDB(exercise: Exercise) {
        val key = DataBaseEntry.UNNI_KEY
        customRef.child(key.toString()).child("custom workout info").child(exercise.name!!).removeValue()
    }
}