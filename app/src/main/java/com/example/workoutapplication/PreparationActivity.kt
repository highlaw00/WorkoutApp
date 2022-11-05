package com.example.workoutapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapplication.databinding.ActivityPreparationBinding
import com.google.android.material.tabs.TabLayout

class PreparationActivity : AppCompatActivity() {

    // TODO: 만약 리스트가 비어있다면... 비어있는 scroll view 를 출력하도록 합니다.
    // TODO: REMOVE 버튼을 삭제하고, Floating Button으로 ADD 와 START만 구현합니다.
    // TODO: 단, REMOVE는 리사이클러 뷰 안의 컴포넌트를 스와이프할 시 삭제할 수 있도록 합니다.
    // TODO: 추가로, DRAG하여 순서를 조작할 수 있게 하며, TOUCH 시 SET 정보(중량과 세트 횟수)를 기록할 수 있도록 합니다.


    object List {
        val BenchSetList = arrayListOf(Sets(10,80.0), Sets(10,80.0), Sets(10,80.0))
        val SquatSetList = arrayListOf(Sets(5,100.0), Sets(3,110.0), Sets(1,120.0))
        val DeadSetList = arrayListOf(Sets(8,110.0), Sets(8,115.0), Sets(8,115.0))

        var workoutList = arrayListOf(
            Exercise(R.drawable.bench_press, "Bench Press",  "Chest", BenchSetList),
            Exercise(R.drawable.squat, "Squat",  "Legs", SquatSetList),
            Exercise(R.drawable.deadlift, "Dead lift", "Legs", DeadSetList),
            Exercise(R.drawable.bench_press, "Bench Press",  "Chest", BenchSetList),
            Exercise(R.drawable.squat, "Squat",  "Legs", SquatSetList),
            Exercise(R.drawable.deadlift, "Dead lift", "Legs", DeadSetList)
        )

        var dietList = arrayListOf(
            Diet(name = "Rice", description = "Test_description", ingredient = 60, weight = 100),
            Diet(name = "Chicken Breast", description = "Test_description", ingredient = 60, weight = 100),
            Diet(name = "Broccoli", description = "Test_description", ingredient = 60, weight = 100)
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

        // 초기 Fragment는 Workout Fragment로 고정해주기 위한 함수
        initFragmentUi()

        // Tab 버튼 눌렀을 때 Fragment 전환해주는 Listener
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