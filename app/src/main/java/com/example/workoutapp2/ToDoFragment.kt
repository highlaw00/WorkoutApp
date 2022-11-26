package com.example.workoutapp2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.workoutapp2.databinding.FragmentToDoBinding
import com.example.workoutapp2.viewmodel.ExerciseViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.FirebaseFirestore

class ToDoFragment : Fragment(){
    private val viewModel: ExerciseViewModel by activityViewModels()
    var keyData: String? = null
    var currentDate: String? = null
    var binding: FragmentToDoBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            keyData = it.getString("KEY")
            currentDate = it.getString("DATE")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentToDoBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager: ViewPager2? = binding?.vpTodoList
        val tabLayout: TabLayout? = binding?.tabLayout
        val viewPagerFragmentAdapter = ViewpagerFragmentAdapter(this, 0)
        viewPager?.adapter = viewPagerFragmentAdapter

        val tabTitles = listOf("운동", "식단")
        tabLayout?.let {
            if (viewPager != null) {
                TabLayoutMediator(it, viewPager, { tab, position -> tab.text = tabTitles[position]}).attach()
            }
        }

        // 아래 코드는 add fragment로 가는 listener 설정 코드입니다.
        val bundle = Bundle().apply {
            putString("KEY", keyData)
        }
        binding?.btnAdd?.setOnClickListener {
            findNavController().navigate(R.id.action_toDoFragment_to_addFragment, bundle)
        }
        binding?.btnStart?.setOnClickListener {
            findNavController().navigate(R.id.action_toDoFragment_to_timerFragment, bundle)
        }
    }
}