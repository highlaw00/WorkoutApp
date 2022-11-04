package com.example.workoutapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SetsAdapter(val setsList: ArrayList<Sets>) : RecyclerView.Adapter<SetsAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetsAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sets_list_view, parent, false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos = adapterPosition
                val set: Sets = setsList[curPos]
            }
        }
    }

    override fun onBindViewHolder(holder: SetsAdapter.CustomViewHolder, position: Int) {
        val setString = "Set "
        holder.setNum.text = setString + (position + 1).toString()
        holder.weight.text = setsList[position].weight.toString()
        holder.reps.text = setsList[position].reps.toString()
    }

    override fun getItemCount(): Int {
        return setsList.size
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val setNum: TextView = itemView.findViewById<TextView>(R.id.tv_sets_number)
        val weight: TextView = itemView.findViewById<TextView>(R.id.tv_sets_weight)
        val reps:TextView = itemView.findViewById<TextView>(R.id.tv_sets_reps)
    }
}