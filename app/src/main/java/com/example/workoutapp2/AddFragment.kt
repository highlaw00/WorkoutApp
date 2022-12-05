package com.example.workoutapp2

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.workoutapp2.databinding.AddDialogBinding
import com.example.workoutapp2.databinding.FragmentAddBinding
import com.example.workoutapp2.viewmodel.ExerciseViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class AddFragment : Fragment() {
    var binding: FragmentAddBinding? = null
    private val viewModel: ExerciseViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager: ViewPager2? = binding?.vpList
        val tabLayout: TabLayout? = binding?.tabAddLayout

        val viewPagerFragmentAdapter = ViewpagerFragmentAdapter(this)
        viewPager?.adapter = viewPagerFragmentAdapter
        val tabTitles = listOf("전체", "가슴", "등", "팔", "어깨", "하체", "복근")
        tabLayout?.let {
            if (viewPager != null) {
                TabLayoutMediator(it, viewPager) { tab, position ->
                    tab.text = tabTitles[position]
                }.attach()
            }
        }

        binding?.btnAddToDb?.setOnClickListener {
            val dialogBinding = AddDialogBinding.inflate(layoutInflater)
            val dialog = this.context?.let { it1 ->
                AlertDialog.Builder(it1).run {
                    setView(dialogBinding.root)
                    setPositiveButton("추가", null)
                    setNegativeButton("취소", null)
                    show()
                }
            }
            dialog?.getButton(DialogInterface.BUTTON_POSITIVE)?.setOnClickListener {
                val name = dialogBinding.etName.text.toString()
                val partId = dialogBinding.rgPart.checkedRadioButtonId
                if (name.isNotBlank() and (partId != -1)){
                    val partString = when (dialogBinding.rgPart.findViewById<RadioButton>(partId).text) {
                        "가슴" -> "Chest"
                        "등" -> "Back"
                        "팔" -> "Arm"
                        "어깨" -> "Delts"
                        "하체" -> "Legs"
                        "복근" -> "Abs"
                        else -> "Undefined"
                    }
                    val newExercise = Exercise(name = name, part = partString, isMainExercise = false)
                    if (viewModel.isValid(newExercise)) {
                        viewModel.addToCustom(newExercise)
                        dialog?.dismiss()
                    }else {
                        Toast.makeText(this.context, "이미 존재하는 운동입니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this.context, "운동의 이름과 부위를 선택해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}