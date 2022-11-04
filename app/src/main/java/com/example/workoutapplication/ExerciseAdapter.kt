package com.example.workoutapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(val exerciseList: ArrayList<Exercise>) : RecyclerView.Adapter<ExerciseAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseAdapter.CustomViewHolder {
        // xml 과 붙여주는 함수
        val view = LayoutInflater.from(parent.context).inflate(R.layout.workout_list_view, parent,false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos = adapterPosition
                val exercise: Exercise = exerciseList[curPos] // 객체 가져오기
            }
        }
    }

    override fun onBindViewHolder(holder: ExerciseAdapter.CustomViewHolder, position: Int) {
//        holder.img.setImageResource(exerciseList.get(position).img)
//        holder.name.text = exerciseList.get(position).name
//        holder.desc.text = exerciseList.get(position).description
        val currentExercise = exerciseList[position]
        holder.img.setImageResource(currentExercise.img)
        holder.name.text = currentExercise.name
        holder.desc.text = currentExercise.description

        val setsLayoutManager = LinearLayoutManager(holder.recyclerView.context, LinearLayoutManager.VERTICAL, false)

        holder.recyclerView.apply {
            layoutManager = setsLayoutManager
            adapter = SetsAdapter(currentExercise.sets)
            setRecycledViewPool(recycledViewPool)
        }
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.iv_workout_image) // 운동 사진
        val name = itemView.findViewById<TextView>(R.id.tv_workout_name) // 이름
        val desc = itemView.findViewById<TextView>(R.id.tv_workout_desc) // 설명
        val recyclerView: RecyclerView = itemView.findViewById<RecyclerView>(R.id.rv_set_list)
    }
}