package com.example.workoutapp2

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp2.databinding.FragmentAddListBinding
import com.example.workoutapp2.viewmodel.ExerciseViewModel
import com.google.firebase.firestore.FirebaseFirestore

class AddListFragment(val part: String) : Fragment() {
    // 문자열을 하나 받아 추후 ViewModel에서 값을 가져옵니다.

    private val viewModel: ExerciseViewModel by activityViewModels()

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
        var newAdapter = WorkoutMainAdapter(listByPart)
        addListBinding.rvMainWorkoutList.adapter = newAdapter
        newAdapter.setOnItemClickListener(object:  WorkoutMainAdapter.onItemClickListener{
            override fun onItemClick(position: Int, command: CommandSymbol) {
                Toast.makeText(this@AddListFragment.context, "You Selected $position item to ${if (command == CommandSymbol.ADD) "add" else "remove"}",Toast.LENGTH_SHORT).show()

                this@AddListFragment.context?.let { AlertDialog.Builder(it)
                    .setMessage("오늘 할 운동에 추가할까요?: ${newAdapter.getItemByPosition(position)?.name}")
                    .setPositiveButton("추가", null)
                    .setNegativeButton("취소", null)
                    .show()
                }
            }


        })
        addListBinding.rvMainWorkoutList.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))

        viewModel.wholeList.observe(viewLifecycleOwner) { wholeList ->
            listByPart = viewModel.getListByPart(part)
            newAdapter.updateList(listByPart)
        }
    }

    override fun onDestroyView() {
        Log.d("debug list fragment", "Destroyed View.")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("debug list fragment", "Destroyed.")
        super.onDestroy()
    }

}