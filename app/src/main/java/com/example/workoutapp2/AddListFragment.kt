package com.example.workoutapp2

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp2.databinding.FragmentAddBinding
import com.example.workoutapp2.databinding.FragmentAddListBinding
import com.example.workoutapp2.viewmodel.ExerciseViewModel

class AddListFragment(val part: String) : Fragment() {
    // 문자열을 하나 받아 추후 ViewModel에서 값을 가져옵니다.
    var binding: FragmentAddListBinding? = null
    private val viewModel: ExerciseViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddListBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var listByPart:MutableList<Exercise>? = null

        binding?.rvMainWorkoutList?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding?.rvMainWorkoutList?.setHasFixedSize(true)

        // adapter 연결
        listByPart = viewModel.getListByPart(part)
        var newAdapter = AddListAdapter(listByPart)
        binding?.rvMainWorkoutList?.adapter = newAdapter

        newAdapter.setRecyclerViewOnClickListener(object:  RecyclerViewOnClickListener{
            override fun onItemClick(position: Int, command: CommandSymbol) {
                val exercise = newAdapter.getItemByPosition(position)
                if (command == CommandSymbol.ADD) {
                    val dialog = this@AddListFragment.context?.let { AlertDialog.Builder(it)
                        .setMessage("오늘 할 운동에 추가할까요?: ${exercise?.name}")
                        .setPositiveButton("추가", null)
                        .setNegativeButton("취소", null)
                        .show()
                    }
                    dialog?.getButton(DialogInterface.BUTTON_POSITIVE)?.setOnClickListener {
                        // 이미 있는 경우 방지 해야함
                        // 만약 exercise의 isMainExercise가 true라면 custom/UUID/workoutInfo/exercise.name 에서 lastReps와 Weights를 가져옴
                        viewModel.addToDaily(exercise)
                        Toast.makeText(this@AddListFragment.context, "${exercise?.name} 운동이 추가되었습니다.", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }else {
                    val dialog = this@AddListFragment.context?.let { AlertDialog.Builder(it)
                        .setMessage("이 운동을 리스트에서 삭제할까요?: ${newAdapter.getItemByPosition(position)?.name}")
                        .setPositiveButton("삭제", null)
                        .setNegativeButton("취소", null)
                        .show()
                    }
                    dialog?.getButton(DialogInterface.BUTTON_POSITIVE)?.setOnClickListener {
                        viewModel.removeFromCustom(exercise)
                        Toast.makeText(this@AddListFragment.context, "${exercise?.name} 운동이 리스트에서 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }

            }

            override fun onSetClick(position: Int, command: CommandSymbol, reps: Int, weight: Double) {}

        })
        binding?.rvMainWorkoutList?.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))

        viewModel.wholeList.observe(viewLifecycleOwner) { wholeList ->
            listByPart = viewModel.getListByPart(part)
            newAdapter.updateList(listByPart)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}