package com.example.workoutapp2

interface RecyclerViewOnClickListener {
    fun onItemClick(position: Int, command: CommandSymbol)
    fun onSetClick(position: Int, command: CommandSymbol, reps: Int, weight: Double)
}