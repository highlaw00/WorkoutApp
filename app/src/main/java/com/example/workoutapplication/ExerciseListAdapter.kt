package com.example.workoutapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.workoutapplication.databinding.SubListViewBinding

class ExerciseListAdapter (val context: Context, val exerciseList: ArrayList<Exercise>): BaseAdapter(){
    override fun getCount(): Int {
        return exerciseList.size
    }

    override fun getItem(position: Int): Any {
        return exerciseList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = SubListViewBinding.inflate(LayoutInflater.from(context))

        val profile = binding.ivProfile
        val name = binding.tvName
        val desc = binding.tvDescription

        val exercise = exerciseList[position]

        profile.setImageResource(exercise.img)
        name.text = exercise.name
        desc.text = exercise.description

        return binding.root
    }

}