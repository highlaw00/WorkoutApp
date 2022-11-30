package com.example.workoutapp2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp2.databinding.FragmentWorkoutBinding
import com.example.workoutapp2.viewmodel.ExerciseViewModel

class WorkoutToDoFragment : Fragment() {

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
        var listToDo: MutableList<Exercise>? = null

        binding?.rvWorkoutList?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        binding?.rvWorkoutList?.setHasFixedSize(true)

        listToDo = viewModel.todoList.value?.toMutableList()
        val newAdapter = WorkoutToDoAdapter(listToDo)

        binding?.rvWorkoutList?.adapter = newAdapter
        binding?.rvWorkoutList?.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))

        newAdapter.setRecyclerViewOnClickListener(object: RecyclerViewOnClickListener {
            override fun onItemClick(position: Int, command: CommandSymbol) {}
            override fun onSetClick(position: Int, command: CommandSymbol, reps: Int, weight: Double) {
                if (command == CommandSymbol.ADD){
                    viewModel.updateSetByPosition(position, reps, weight)
                } else {
                    viewModel.deleteLastSet(position)
                }
            }
        })

        viewModel.todoList.observe(viewLifecycleOwner) { todoList ->
            listToDo = viewModel.todoList.value?.toMutableList()
            newAdapter.updateList(listToDo)
        }
    }
}