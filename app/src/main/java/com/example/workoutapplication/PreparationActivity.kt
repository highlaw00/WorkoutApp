package com.example.workoutapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapplication.databinding.ActivityPreparationBinding
import com.google.android.material.tabs.TabLayout

class PreparationActivity : AppCompatActivity() {

    object List {
        var workoutList = arrayListOf(
            Exercise(R.drawable.bench_press, "Bench Press", 80, "Chest"),
            Exercise(R.drawable.squat, "Squat", 80, "Chest"),
            Exercise(R.drawable.deadlift, "Dead lift", 80, "Chest")
        )

        var dietList = arrayListOf(
            Diet(name = "Rice", description = "Test_description", ingredient = 60, weight = 100),
            Diet(name = "Chicken Breast", description = "Test_description", ingredient = 60, weight = 100),
            Diet(name = "Brocoli", description = "Test_description", ingredient = 60, weight = 100)
        )
    }

    private lateinit var preBinding: ActivityPreparationBinding

    private fun initFragmentUi() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame, WorkoutFragment()).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preBinding = ActivityPreparationBinding.inflate(layoutInflater)
        setContentView(preBinding.root)

        initFragmentUi()

        preBinding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                when (tab?.text) {
                    "Workout" -> transaction.replace(R.id.main_frame, WorkoutFragment())
                    "Diet" -> transaction.replace(R.id.main_frame, DietFragment())
                }
                transaction.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

    }
}