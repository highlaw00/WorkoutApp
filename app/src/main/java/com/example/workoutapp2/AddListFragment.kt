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
        var listByPart:MutableList<Exercise>? = null

        addListBinding.rvMainWorkoutList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        addListBinding.rvMainWorkoutList.setHasFixedSize(true)

        // adapter 연결
        listByPart = viewModel.getListByPart(part)
        var sizeOfList = listByPart?.size
        val newAdapter = WorkoutMainAdapter(listByPart)
        addListBinding.rvMainWorkoutList.adapter = newAdapter
        addListBinding.rvMainWorkoutList.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))

        viewModel.wholeList.observe(viewLifecycleOwner) { wholeList ->
            val sizeOfViewModel = viewModel.getListByPart(part)?.size
            if (sizeOfList != sizeOfViewModel) {
                listByPart = viewModel.getListByPart(part)
                sizeOfList = listByPart?.size
                newAdapter.updateList(listByPart)
            }
        }
    }

}