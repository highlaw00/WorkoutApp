package com.example.workoutapp2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp2.databinding.FragmentAddListBinding
import com.example.workoutapp2.viewmodel.ExerciseViewModel
import com.google.firebase.firestore.FirebaseFirestore

class AddListFragment(val part: String, var list: List<Exercise>) : Fragment() {
    // 문자열을 하나 받아 추후 ViewModel에서 값을 가져옵니다.

    private var fragmentString: String? = null

    private fun showList(binding: FragmentAddListBinding) {

        // list 카테고리에 따라 정렬합니다.
        if (part == "All") {
            // list를 건들지 않음
        } else {
            // 카테고리별로 정리합니다.
            val newList = mutableListOf<Exercise>()
            for (exercise in list) {
                Log.d("yool","exercise: ${exercise}")
                if (exercise.part == part) {
                    newList.add(exercise)
                }
            }
            this.list = newList.toList()
            Log.d("yool",this.list.toString())
        }

        // Fragment에서 전달받은 list를 넘기면서 ListAdapter 생성
        binding.rvMainWorkoutList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvMainWorkoutList.setHasFixedSize(true)

        // adapter 연결
        binding.rvMainWorkoutList.adapter = WorkoutMainAdapter(list)
        binding.rvMainWorkoutList.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
    }
//
//    private fun getList(binding: FragmentAddListBinding) {
//        // fragment 전달 값에 따라 db에서 운동들을 가져오고 보여줍니다.
//        // coroutines를 사용하여 리스트를 갖고오는 것도 보여주는 것을 나눠줄 수 있지만 먼저 사용하지 않고 구현
//        val mainDB = FirebaseFirestore.getInstance().collection(DataBaseEntry.MAIN_DB_STRING)
//        val ret =  ArrayList<Exercise>()
//        var current: Exercise
//        val partString = if (fragmentString == "All") "" else fragmentString
//        when (partString) {
//            fragmentString -> {
//                mainDB.whereEqualTo("part", partString).get()
//                    .addOnSuccessListener { documents ->
//                        // part 명이 동일한 document 들을 모두 가져와 배열에 저장합니다.
//                        for (document in documents) {
//                            current = document.toObject(Exercise::class.java)
//                            ret.add(current)
//                        }
//                        // 그리고 그 배열을 show 해줍니다.
//                        showList(ret, binding)
//                    }.addOnFailureListener { exception ->
//                    }
//            }
//            else -> {
//                mainDB.orderBy("name").get()
//                    .addOnSuccessListener { documents ->
//                        for (document in documents) {
//                            current = document.toObject(Exercise::class.java)
//                            ret.add(current)
//                        }
//                        showList(ret, binding)
//                    }.addOnFailureListener { exception ->
//                    }
//            }
//        }
//
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addListBinding = FragmentAddListBinding.bind(view)
        showList(addListBinding)
    }

}