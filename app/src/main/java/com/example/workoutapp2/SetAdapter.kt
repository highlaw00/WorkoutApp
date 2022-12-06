package com.example.workoutapp2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp2.databinding.ListSetTodoBinding

class SetAdapter(private val exercise: Exercise): RecyclerView.Adapter<SetAdapter.Holder>() {

    class Holder(binding: ListSetTodoBinding): RecyclerView.ViewHolder(binding.root) {
        private val setString = binding.tvCurrentSet
        private val currentWeight = binding.tvCurrentWeight
        private val currentReps = binding.tvCurrentReps

        fun bind(reps: Int, weights: Double, position: Int) {
            this.setString.text = "세트 ${position + 1}"
            this.currentWeight.text = "$weights kg"
            this.currentReps.text = "$reps 회"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListSetTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val reps = exercise.lastReps?.get(position)
        val weight = exercise.lastWeights?.get(position)
        holder.bind(reps!!, weight!!, position)
    }

    override fun getItemCount(): Int {
        return exercise.lastReps?.size ?: 0
    }

}
