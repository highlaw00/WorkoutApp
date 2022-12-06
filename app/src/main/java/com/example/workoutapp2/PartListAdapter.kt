package com.example.workoutapp2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp2.databinding.ListWorkoutMainBinding

class PartListAdapter(private val data: MutableList<Exercise>?) : RecyclerView.Adapter<PartListAdapter.Holder>() {

    private lateinit var myListener: RecyclerViewOnClickListener

    fun setRecyclerViewOnClickListener(listener: RecyclerViewOnClickListener) {
        myListener = listener
    }

    fun updateList(newList: MutableList<Exercise>?) {
        data?.clear()
        if (newList != null) {
            data?.addAll(newList)
        }
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

    class Holder(private val binding: ListWorkoutMainBinding, private val listener: RecyclerViewOnClickListener): RecyclerView.ViewHolder(binding.root) {
        var img = binding.ivWorkoutImageMain
        var name = binding.tvWorkoutNameMain
        var addBtn = binding.btnWorkoutAddMain
        var removeBtn = binding.btnWorkoutRemoveMain

        fun bind(exercise: Exercise) {
            name.text = exercise.name
            img.setImageResource(exercise.img)

            // 메인 리스템의 아이템이 만약 custom에 속한다면 x버튼을 보입니다.
            if (exercise.isMainExercise == false) {
                removeBtn.visibility = View.VISIBLE
                removeBtn.setOnClickListener {
                    listener.onItemClick(adapterPosition, CommandSymbol.REMOVE)
                }
            } else {
                removeBtn.visibility = View.GONE
            }

            addBtn.setOnClickListener {
                listener.onItemClick(adapterPosition, CommandSymbol.ADD)
            }
        }
    }
}