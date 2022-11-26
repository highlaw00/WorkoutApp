package com.example.workoutapp2

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp2.databinding.ListWorkoutMainBinding

class WorkoutMainAdapter(private val data: MutableList<Exercise>?) : RecyclerView.Adapter<WorkoutMainAdapter.Holder>() {

    private lateinit var myListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int, command: CommandSymbol)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {

        myListener = listener

    }

    fun updateList(newList: MutableList<Exercise>?) {
        data?.clear()
        if (newList != null) {
            data?.addAll(newList)
        }
        this.notifyDataSetChanged()
    }

    fun getItemByPosition(position: Int): Exercise? {
        return data?.get(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListWorkoutMainBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return Holder(binding, myListener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val exercise = data?.get(position)
        if (exercise != null) {
            holder.bind(exercise)
        }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    class Holder(private val binding: ListWorkoutMainBinding, private val listener: onItemClickListener): RecyclerView.ViewHolder(binding.root) {
        var img = binding.ivWorkoutImageMain
        var name = binding.tvWorkoutNameMain

        fun bind(exercise: Exercise) {
            name.text = exercise.name
            img.setImageResource(exercise.img)

            binding.btnWorkoutAddMain.setOnClickListener {
                listener.onItemClick(adapterPosition, CommandSymbol.ADD)
            }
            binding.btnWorkoutRemoveMain.setOnClickListener {
                listener.onItemClick(adapterPosition, CommandSymbol.REMOVE)
            }

        }
    }
}