package com.example.workoutapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapplication.databinding.ActivityPreparationBinding

class PreparationActivity : AppCompatActivity() {
    lateinit var preBinding: ActivityPreparationBinding

    var todayList = arrayListOf<Exercise>(
        // 데이터베이스를 설립 후 초기에는 빈 리스트를 전달
        // 이후 add를 통해 원하는 item 추가
        Exercise(R.drawable.bench_press, "Bench Press", 80, "Chest"),
        Exercise(R.drawable.squat, "Squat", 80, "Chest"),
        Exercise(R.drawable.deadlift, "Dead lift", 80, "Chest"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preBinding = ActivityPreparationBinding.inflate(layoutInflater)
        setContentView(preBinding.root)

//        val adapter = ExerciseAdapter(this, todayList)
//        preBinding.listView.adapter = adapter
//        preBinding.listView.setOnItemClickListener { parent, view, position, id ->
//            val selectItem = parent.getItemAtPosition(position) as Exercise
//
//            Toast.makeText(this, selectItem.name, Toast.LENGTH_SHORT).show()
//        }

        // rvList의 layoutManager 변경
        preBinding.rvList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        preBinding.rvList.setHasFixedSize(true)

        // adapter 연결
        preBinding.rvList.adapter = ExerciseAdapter(todayList)
        preBinding.rvList.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        preBinding.btnAdd.setOnClickListener {
            todayList.add(Exercise(name = "Test Name", weight = 0, description = "Test Description"))
            preBinding.rvList.adapter = ExerciseAdapter(todayList)
        }
    }
}