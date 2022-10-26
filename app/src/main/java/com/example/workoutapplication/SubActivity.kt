package com.example.workoutapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.workoutapplication.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {
    lateinit var subBinding: ActivitySubBinding

    // 실질적인 Exercise 클래스가 담기는 배열.
    var exerciseList = arrayListOf<Exercise>(
        Exercise(R.drawable.bench_press, "Bench Press", 80, "Chest"),
        Exercise(R.drawable.deadlift, "Dead Lift", 120, "Legs"),
        Exercise(R.drawable.squat, "Squat", 110, "Legs")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subBinding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(subBinding.root)

//        ExerciseListAdapter 가 하는 일:
//        SubListView.xml 파일과 Exercise 클래스를 이어주는 어댑터 역할을 합니다.
        val adapter = ExerciseListAdapter(this, exerciseList)
        subBinding.listView.adapter = adapter
//        화면에 출력된 리스트에서 아이템을 클릭했을 때 토스트 메세지를 출력 해줍니다.
        subBinding.listView.setOnItemClickListener { parent, view, position, id ->
            val selectItem = parent.getItemAtPosition(position) as Exercise

//          intent를 통해 다음 화면에 전달합니다.
            val intent = Intent(this, SetWorkoutInfo::class.java)
            intent.putExtra("workoutName", selectItem.name.toString())
            startActivity(intent)
        }
    }
}