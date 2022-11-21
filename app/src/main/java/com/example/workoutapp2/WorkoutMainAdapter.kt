package com.example.workoutapp2

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp2.databinding.ListWorkoutMainBinding

class WorkoutMainAdapter(private val data: LiveData<ArrayList<Exercise>>) : RecyclerView.Adapter<WorkoutMainAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListWorkoutMainBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val exercise = data.value?.get(position)
        if (exercise != null) {
            with (holder) {
                name.text = exercise.name
                part.text = exercise.part
                img.setImageResource(exercise.img)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.value?.size ?: 0
    }

    class Holder(private val binding: ListWorkoutMainBinding): RecyclerView.ViewHolder(binding.root) {
        var img = binding.ivWorkoutImageMain
        var name = binding.tvWorkoutNameMain
        var part = binding.tvWorkoutDescMain
    }
}