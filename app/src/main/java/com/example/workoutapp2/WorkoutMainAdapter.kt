package com.example.workoutapp2

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp2.databinding.ListWorkoutMainBinding

class WorkoutMainAdapter(private val data: MutableList<Exercise>?) : RecyclerView.Adapter<WorkoutMainAdapter.Holder>() {

    fun updateList(newList: MutableList<Exercise>?) {
        data?.clear()
        if (newList != null) {
            data?.addAll(newList)
        }
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListWorkoutMainBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        // 이곳에서 라이브 데이터를 만지고 놀아가지고 특정 운동만 뽑는 방법을 할것이냐 (x)
        // 아니면 viewmodel에 라이브 데이터를 여러개 만들것이냐
        // 아니면 ...
        val exercise = data?.get(position)
        with (holder) {
            name.text = exercise?.name
            part.text = exercise?.part
            if (exercise != null) {
                img.setImageResource((exercise.img))
            }
        }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    class Holder(private val binding: ListWorkoutMainBinding): RecyclerView.ViewHolder(binding.root) {
        var img = binding.ivWorkoutImageMain
        var name = binding.tvWorkoutNameMain
        var part = binding.tvWorkoutDescMain
    }
}