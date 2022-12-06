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
import com.example.workoutapp2.databinding.FragmentAddListBinding
import com.example.workoutapp2.viewmodel.ExerciseViewModel

/**
 * PartListFragment.kt
 *
 *  1. viewModel 에서 부분 별 운동 리스트를 가져와 Display 합니다.
 *
 *  2. 만약 viewModel 이 변경되면 observe 한 뒤 변경사항을 update 합니다.
 *
 *  3. 추가 버튼, 삭제 버튼에 대한 event 발생 시 handle 합니다.
 */

class PartListFragment(private val part: String) : Fragment() {

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

        listByPart = viewModel.getListByPart(part)
        var newAdapter = PartListAdapter(listByPart)
        binding?.rvMainWorkoutList?.adapter = newAdapter

        newAdapter.setRecyclerViewOnClickListener(object:  RecyclerViewOnClickListener{
            override fun onItemClick(position: Int, command: CommandSymbol) {
                val exercise = newAdapter.getItemByPosition(position)
                if (command == CommandSymbol.ADD) {
                    val dialog = this@PartListFragment.context?.let { AlertDialog.Builder(it)
                        .setMessage("오늘 할 운동에 추가할까요?: ${exercise?.name}")
                        .setPositiveButton("추가", null)
                        .setNegativeButton("취소", null)
                        .show()
                    }
                    dialog?.getButton(DialogInterface.BUTTON_POSITIVE)?.setOnClickListener {
                        viewModel.addToDaily(exercise)
                        Toast.makeText(this@PartListFragment.context, "${exercise?.name} 운동이 추가되었습니다.", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }else {
                    val dialog = this@PartListFragment.context?.let { AlertDialog.Builder(it)
                        .setMessage("이 운동을 리스트에서 삭제할까요?: ${newAdapter.getItemByPosition(position)?.name}")
                        .setPositiveButton("삭제", null)
                        .setNegativeButton("취소", null)
                        .show()
                    }
                    dialog?.getButton(DialogInterface.BUTTON_POSITIVE)?.setOnClickListener {
                        viewModel.removeFromCustom(exercise)
                        Toast.makeText(this@PartListFragment.context, "${exercise?.name} 운동이 리스트에서 삭제되었습니다.", Toast.LENGTH_SHORT).show()
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
            newAdapter.notifyDataSetChanged()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}