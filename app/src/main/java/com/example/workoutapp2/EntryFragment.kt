package com.example.workoutapp2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.workoutapp2.databinding.FragmentEntryBinding

class EntryFragment : Fragment() {

    var binding: FragmentEntryBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEntryBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnEntryOk?.setOnClickListener {
            val datePicker = binding?.dpEntryDp
            val currentDateSelected = datePicker?.year.toString() + datePicker?.month.toString() + datePicker?.month.toString()
            val keyData = DataBaseEntry.UNNI_KEY
            val bundle = Bundle().apply {
                putString("KEY", keyData)
                putString("DATE", currentDateSelected)
            }
            findNavController().navigate(R.id.action_entryFragment_to_toDoFragment2, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}