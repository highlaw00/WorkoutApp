package com.example.workoutapp2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.workoutapp2.databinding.FragmentToDoBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.FirebaseFirestore

class ToDoFragment : Fragment() {
    var keyData: String? = null
    var currentDate: String? = null
    var binding: FragmentToDoBinding? = null

    private fun initDataBase(date: String?) {
        if (date == null) return
        val uniqueDocRef = FirebaseFirestore.getInstance().collection(DataBaseEntry.DAILY_DB_STRING)
            .document(DataBaseEntry.UNNI_KEY ?:"Error Document")
        val dailyDocRef = uniqueDocRef.collection(DataBaseEntry.DAILY_RECORD_INFO_STRING).document(date)

        dailyDocRef.get()
            .addOnSuccessListener { document ->
                if (document.data == null) {
                    dailyDocRef.set(mapOf("isEmpty" to true))
                } else {
                    Log.d("yool", "Document id: ${document.id}, data: ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("yool", "Failed with",exception)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            keyData = it.getString("KEY")
            currentDate = it.getString("DATE")
        }
        initDataBase(currentDate)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentToDoBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // todo: get Data From Database and show it

        // 아래 코드는 tablayout에 ViewPager2 와 Fragment 를 적용한 코드입니다.
        val viewPager: ViewPager2? = binding?.vpTodoList
        val tabLayout: TabLayout? = binding?.tabLayout

        val viewPagerFragmentAdapter = this.activity?.let { ViewpagerFragmentAdapter(it, 0) }
        viewPager?.adapter = viewPagerFragmentAdapter

        val tabTitles = listOf<String>("운동", "식단")
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
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            ToDoFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}