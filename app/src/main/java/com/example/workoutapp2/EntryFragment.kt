package com.example.workoutapp2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.workoutapp2.databinding.FragmentEntryBinding
import com.example.workoutapp2.viewmodel.ExerciseViewModel
import java.time.format.DateTimeFormatter

/**
 *  EntryFragment.kt
 *
 *  1. 사용자가 날짜를 고른 뒤, 확인 버튼을 누르면 viewModel 에 해당 날짜를 전달합니다.
 *
 *  2. viewModel 에선 해당 날짜에 따라 todoList 를 observing 합니다.
 *
 **/

class EntryFragment : Fragment() {
    private var binding: FragmentEntryBinding? = null
    private val viewModel: ExerciseViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEntryBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnEntryOk?.setOnClickListener {
            val datePicker = binding?.dpEntryDp
            val currentSelectedDate = "${datePicker?.year.toString()}-${(datePicker?.month?.plus(1)).toString()}-${datePicker?.dayOfMonth.toString()}"
            viewModel.setDate(currentSelectedDate)
            findNavController().navigate(R.id.action_entryFragment_to_toDoFragment2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}