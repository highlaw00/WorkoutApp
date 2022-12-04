package com.example.workoutapp2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp2.databinding.FragmentToDoWithContentsBinding
import com.example.workoutapp2.viewmodel.ExerciseViewModel

class ToDoWithContentsFragment : Fragment() {
    var binding: FragmentToDoWithContentsBinding? = null
    val viewModel: ExerciseViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentToDoWithContentsBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var listToDo: MutableList<Exercise>? = null

        binding?.rvTodoWorkoutList?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        binding?.rvTodoWorkoutList?.setHasFixedSize(true)

        listToDo = viewModel.todoList.value?.toMutableList()
        val newAdapter = WorkoutToDoAdapter(listToDo)

        binding?.rvTodoWorkoutList?.adapter = newAdapter
        binding?.rvTodoWorkoutList?.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))

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