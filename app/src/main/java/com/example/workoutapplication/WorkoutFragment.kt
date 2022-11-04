package com.example.workoutapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapplication.databinding.FragmentWorkoutBinding
import com.example.workoutapplication.databinding.SetsListViewBinding

class WorkoutFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
    }
}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val workoutBinding = FragmentWorkoutBinding.bind(view)

        // Fragment에서 전달받은 list를 넘기면서 ListAdapter 생성
        workoutBinding.rvWorkoutList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        workoutBinding.rvWorkoutList.setHasFixedSize(true)

        // adapter 연결
        workoutBinding.rvWorkoutList.adapter = ExerciseAdapter(PreparationActivity.List.workoutList)
        workoutBinding.rvWorkoutList.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            DietFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}