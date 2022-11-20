package com.example.workoutapp2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp2.databinding.ListWorkoutTodoBinding

class WorkoutToDoAdapter(val exerciseList: List<Exercise>?) : RecyclerView.Adapter<WorkoutToDoAdapter.Holder>() {
    class Holder(private val binding: ListWorkoutTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise) {
            binding.ivWorkoutImage.setImageResource(exercise.img)
            binding.tvWorkoutName.text = exercise.name
            binding.tvWorkoutDesc.text = exercise.part

            // set 정보는 추후에 추가합니다.
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListWorkoutTodoBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(exerciseList?.get(position) ?: Exercise())
    }

    override fun getItemCount(): Int {
        if (exerciseList != null) return exerciseList.size
        else return 0
    }
}