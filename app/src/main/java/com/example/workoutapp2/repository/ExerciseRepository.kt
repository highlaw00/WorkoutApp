package com.example.workoutapp2.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.workoutapp2.DataBaseEntry
import com.example.workoutapp2.Exercise
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.json.JSONObject

class ExerciseRepository() {
    private val database = Firebase.database
    private val mainRef = database.getReference("main")
    private val customRef = database.getReference("custom")

    fun observeMainList(list: MutableLiveData<List<Exercise>>) {
        mainRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList = mutableListOf<Exercise>()
                for (child in snapshot.children) {
                    child.getValue(Exercise::class.java)?.let { tempList.add(it) } }
                list.value = tempList.toList()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("yool", error.toString())
            }

        })
    }

    fun observeToDoList(list: MutableLiveData<List<Exercise>>) {
        val key = DataBaseEntry.UNNI_KEY

        customRef.child(key ?: "ID Error.").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList = mutableListOf<Exercise>()
                for (child in snapshot.children) {
                    child.getValue(Exercise::class.java)?. let { tempList.add(it) } }
                list.value = tempList.toList()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("yool", error.toString())
            }
        })
    }

    fun postToCustom(exercise: Exercise) {
        val key = DataBaseEntry.UNNI_KEY
        customRef.child(key ?: "ID Error").child(exercise.name!!).setValue(exercise)
    }
}