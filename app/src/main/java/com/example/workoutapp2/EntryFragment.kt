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

class EntryFragment : Fragment() {
    var binding: FragmentEntryBinding? = null
    val viewModel: ExerciseViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEntryBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // main List 설정

        // entry fragment 단계에서 db에 todoList
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