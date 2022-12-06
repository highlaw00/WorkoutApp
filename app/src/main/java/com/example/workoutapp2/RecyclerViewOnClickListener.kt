package com.example.workoutapp2

/**
 *
 * RecyclerView 에서 click 이벤트가 발생한 경우
 * adapter 가 아닌 fragment 에서 해결해주기 위한 listener interface 입니다.
 *
 * **/
interface RecyclerViewOnClickListener {
    fun onItemClick(position: Int, command: CommandSymbol)
    fun onSetClick(position: Int, command: CommandSymbol, reps: Int, weight: Double)
}