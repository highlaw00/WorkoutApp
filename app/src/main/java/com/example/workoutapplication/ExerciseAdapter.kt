package com.example.workoutapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(val exerciseList: ArrayList<Exercise>) : RecyclerView.Adapter<ExerciseAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseAdapter.CustomViewHolder {
        // xml 과 붙여주는 함수
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sub_list_view, parent,false)
        // parent의 context(정보) 를 담고 있는 parent.context, 이곳에선 MainActivity가 parent이다
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos = adapterPosition
                val exercise: Exercise = exerciseList.get(curPos) // 객체 가져오기
                Toast.makeText(parent.context, "이름: ${exercise.name}, \n부위: ${exercise.description}, \n중량: ${exercise.weight}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onBindViewHolder(holder: ExerciseAdapter.CustomViewHolder, position: Int) {
        holder.img.setImageResource(exerciseList.get(position).img)
        holder.name.text = exerciseList.get(position).name
        holder.weight.text = exerciseList.get(position).weight.toString()
        holder.desc.text = exerciseList.get(position).description

    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.iv_profile) // 운동 사진
        val name = itemView.findViewById<TextView>(R.id.tv_name) // 이름
        val weight = itemView.findViewById<TextView>(R.id.tv_weight) // 중량
        val desc = itemView.findViewById<TextView>(R.id.tv_description) // 설명
    }
}