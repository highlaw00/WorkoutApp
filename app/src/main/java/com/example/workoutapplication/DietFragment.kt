package com.example.workoutapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapplication.databinding.FragmentDietBinding

class DietFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_diet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDietBinding.bind(view)

        // Fragment에서 전달받은 list를 넘기면서 ListAdapter 생성
        binding.rvList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvList.setHasFixedSize(true)

        // adapter 연결
        binding.rvList.adapter = DietAdapter(PreparationActivity.List.dietList)
        binding.rvList.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            DietFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}