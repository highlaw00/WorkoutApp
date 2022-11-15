package com.example.workoutapp2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workoutapp2.databinding.FragmentDietBinding

class DietFragment : Fragment() {
    var binding: FragmentDietBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDietBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        // Fragment에서 전달받은 list를 넘기면서 ListAdapter 생성
//        binding.rvDietList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//        binding.rvDietList.setHasFixedSize(true)
//
//        // adapter 연결
//        binding.rvDietList.adapter = DietAdapter(PreparationActivity.List.dietList)
//        binding.rvDietList.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
    }
}