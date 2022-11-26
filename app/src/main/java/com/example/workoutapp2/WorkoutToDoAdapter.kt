package com.example.workoutapp2

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp2.databinding.ListWorkoutTodoBinding

class WorkoutToDoAdapter(private val data: MutableList<Exercise>?) : RecyclerView.Adapter<WorkoutToDoAdapter.Holder>() {
    class Holder(private val binding: ListWorkoutTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        var img = binding.ivWorkoutImage
        var name = binding.tvWorkoutName
        var part = binding.tvWorkoutDesc
        fun bind(exercise: Exercise, pos: Int) {
            Log.d("debugshow todoadapter", "Bind: ${exercise.toString()}")
            this.name.text = exercise.name
            this.part.text = exercise.part
            this.img.setImageResource(exercise.img)
            binding.vhLayout.setOnClickListener {
                Toast.makeText(binding.root.context, "position: $pos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        Log.d("debugshow todoadapter", "OnCreateViewHolder")
        val binding = ListWorkoutTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Log.d("debugshow todoadapter", "OnBindViewHolder")
        val exercise = data?.get(position)
        if (exercise != null) holder.bind(exercise, position)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: MutableList<Exercise>?) {
        data?.clear()
        if (newList != null) {
            data?.addAll(newList)
        }
        this.notifyDataSetChanged()
    }
}