package com.example.workoutapp2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp2.databinding.FragmentWorkoutBinding
import com.example.workoutapp2.viewmodel.ExerciseViewModel
import kotlinx.coroutines.*

class WorkoutFragment : Fragment() {

    // workoutFragment 에서는 전체적인 toDoList 를 가져옵니다.
    private val viewModel: ExerciseViewModel by activityViewModels()

    var binding: FragmentWorkoutBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWorkoutBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.setToDo()
//        binding?.rvWorkoutList?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
//        binding?.rvWorkoutList?.adapter = WorkoutToDoAdapter(viewModel.toDoList.value?.workouts)
//        viewModel.toDoList.observe(viewLifecycleOwner) {
//
//        }
    }
}