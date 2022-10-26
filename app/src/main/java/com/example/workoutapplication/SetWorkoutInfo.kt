package com.example.workoutapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.workoutapplication.databinding.ActivitySetWorkoutInfoBinding
import com.example.workoutapplication.databinding.ActivitySubBinding

class SetWorkoutInfo : AppCompatActivity() {
    lateinit var subBinding: ActivitySetWorkoutInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subBinding = ActivitySetWorkoutInfoBinding.inflate(layoutInflater)
        setContentView(subBinding.root)

        if (intent.hasExtra("workoutName")) {
            subBinding.tvWorkoutName.text = intent.getStringExtra("workoutName")
        }
    }
}