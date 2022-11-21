package com.example.workoutapp2

import android.annotation.SuppressLint
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

class AddListFragment(val part: String) : Fragment() {
    // 문자열을 하나 받아 추후 ViewModel에서 값을 가져옵니다.

    private val viewModel: ExerciseViewModel by activityViewModels()

    private fun showList(binding: FragmentAddListBinding) {
        // Fragment에서 전달받은 list를 넘기면서 ListAdapter 생성

    }

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
        addListBinding.rvMainWorkoutList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        addListBinding.rvMainWorkoutList.setHasFixedSize(true)

        // adapter 연결
        addListBinding.rvMainWorkoutList.adapter = WorkoutMainAdapter(viewModel.wholeList)
        addListBinding.rvMainWorkoutList.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))

        viewModel.wholeList.observe(viewLifecycleOwner) { wholeList ->
            addListBinding.rvMainWorkoutList.adapter?.notifyDataSetChanged()
            Log.d("debugshow", viewModel.wholeList.value.toString())
        }

        // 1. viewModel 의 mainList 와 customList 를 어떻게 하나로 묶어서 adapter 에 전달할 것인지
        // mainList 와 customList 둘 다 livedata 이다.
        // liveData 를 병합하는 것은 쉽지 않으니 adapter에 넘겨 줄 때는 list로 변환
        // viewModel 에서 filter 함수를 작성
            // mainList 와 customList 에서 종목별로 list를 받아오는 함수를 작성
        // 2. 그렇게 adapter 에 list 를 전달했다. 이제 update가 된다면?
        // ...  모르겠다...
    }

}