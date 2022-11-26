package com.example.workoutapp2.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.workoutapp2.DataBaseEntry
import com.example.workoutapp2.Exercise
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ExerciseRepository() {
    private val database = Firebase.database
    private val mainRef = database.getReference("main")
    private val customRef = database.getReference("custom")
    private val dailyRef = database.getReference("daily")

    fun postToCustom(exercise: Exercise) {
        val key = DataBaseEntry.UNNI_KEY
        customRef.child(key ?: "ID Error").child(exercise.name!!).setValue(exercise)
    }

    fun observeWholeList(list: MutableLiveData<ArrayList<Exercise>>) {
        // 전체 리스트를 observe하며 바뀐것이 있으면 call back을 해줍니다.
        // livedata 의 class 를 list 나 arraylist로 하는 경우 add를 통해 새로운 원소를 삽입하는데 이는 observer를 작동시키지 않는다!
        // 그래서 새로운 리스트를 선언 후 postValue 함수로 observer를 작동시켜야합니다

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
                Log.d("debugshow mainRef", "list.value: ${list.value ?: "empty"}")
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("yool", error.toString())
            }

        })

        val key = DataBaseEntry.UNNI_KEY
        customRef.child(key ?: "ID Error.").addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val tempList: ArrayList<Exercise>? = list.value
                snapshot.getValue(Exercise::class.java)?.let { tempList?.add(it) }
                tempList?.sortBy { it.name }
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

    fun observeToDoList(list: MutableLiveData<ArrayList<Exercise>>, date: String) {
        val key = DataBaseEntry.UNNI_KEY
        dailyRef.child(key ?: "ID Error.").child(date).addChildEventListener(object: ChildEventListener {
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
}