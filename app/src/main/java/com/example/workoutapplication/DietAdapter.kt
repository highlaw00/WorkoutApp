package com.example.workoutapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapplication.PreparationActivity.List.dietList

class DietAdapter(val exerciseList: ArrayList<Diet>) : RecyclerView.Adapter<DietAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietAdapter.CustomViewHolder {
        // xml 과 붙여주는 함수
        val view = LayoutInflater.from(parent.context).inflate(R.layout.diet_list_view, parent,false)
        // parent의 context(정보) 를 담고 있는 parent.context, 이곳에선 MainActivity가 parent이다
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos = adapterPosition
                val diet: Diet = dietList[curPos] // 객체 가져오기
            }
        }
    }

    override fun onBindViewHolder(holder: DietAdapter.CustomViewHolder, position: Int) {
        holder.img.setImageResource(dietList[position].img)
        holder.name.text = dietList[position].name
        holder.weight.text = dietList[position].weight.toString()
        holder.desc.text = dietList[position].description
        holder.ingredient.text = dietList[position].ingredient.toString()
        // 칼로리 정보 추가해야 함

    }

    override fun getItemCount(): Int {
        return dietList.size
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.iv_workout_image) // 운동 사진
        val name = itemView.findViewById<TextView>(R.id.tv_workout_name) // 이름
        val weight = itemView.findViewById<TextView>(R.id.tv_diet_weight) // 중량
        val desc = itemView.findViewById<TextView>(R.id.tv_workout_desc) // 설명
        val ingredient = itemView.findViewById<TextView>(R.id.tv_diet_ingredients) // 칼로리
    }
}