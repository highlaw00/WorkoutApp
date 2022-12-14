package com.example.workoutapp2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.DragStartHelper
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.workoutapp2.databinding.FragmentToDoBinding
import com.example.workoutapp2.viewmodel.ExerciseViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.FirebaseFirestore

/**
 *  ToDoFragment.kt
 *
 *  1. 이전 EntryFragment 에서 todoList 를 observe 시작했기에 todoList 를 가져옵니다.
 *
 *  2. 가져온 todoList 를 RecyclerViewAdapter 와 연결합니다.
 *
 *  3. Adapter 와 연결한 뒤, 각 운동 별 삭제 및 순서 변경, 각 운동 별 세트 추가 및 삭제를 할 수 있도록 Listener 를 추가합니다.
 *
 */

class ToDoFragment() : Fragment(){
    private val viewModel: ExerciseViewModel by activityViewModels()
    var binding: FragmentToDoBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentToDoBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var listToDo: MutableList<Exercise>? = null


        binding?.tvDate?.text = "${viewModel.getDate()}"

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

        val itemTouchCallBack = object: ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPos: Int = viewHolder.adapterPosition
                val toPos: Int = target.adapterPosition
                viewModel.swapExercise(fromPos, toPos)
                newAdapter.notifyItemMoved(fromPos, toPos)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position: Int = viewHolder.adapterPosition
                viewModel.removeFromDaily(position)
                newAdapter.removeData(viewHolder.layoutPosition)
            }
        }

        ItemTouchHelper(itemTouchCallBack).attachToRecyclerView(binding?.rvTodoWorkoutList)

        viewModel.todoList.observe(viewLifecycleOwner) {
            listToDo = viewModel.todoList.value?.toMutableList()
            newAdapter.updateList(listToDo)
            newAdapter.notifyDataSetChanged()
        }

        binding?.btnAdd?.setOnClickListener {
            findNavController().navigate(R.id.action_toDoFragment_to_addFragment)
        }
        binding?.btnStart?.setOnClickListener {
            if (viewModel.checkStartValidation()) {
                findNavController().navigate(R.id.action_toDoFragment_to_timerFragment)
            } else {
                Toast.makeText(this.context, "운동 및 세트를 추가해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}