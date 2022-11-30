package com.example.workoutapp2

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp2.databinding.AddSetDialogBinding
import com.example.workoutapp2.databinding.ListWorkoutTodoBinding
import com.example.workoutapp2.viewmodel.ExerciseViewModel

class WorkoutToDoAdapter(private val data: MutableList<Exercise>?) : RecyclerView.Adapter<WorkoutToDoAdapter.Holder>() {

    private lateinit var myListener: RecyclerViewOnClickListener

    fun setRecyclerViewOnClickListener(listener: RecyclerViewOnClickListener) {
        myListener = listener
    }

    class Holder(private val binding: ListWorkoutTodoBinding, private val listener: RecyclerViewOnClickListener) : RecyclerView.ViewHolder(binding.root) {
        var img = binding.ivWorkoutImage
        var name = binding.tvWorkoutName

        private fun setSetAdapter(binding: ListWorkoutTodoBinding, exercise: Exercise) {
            binding.rvSetList.layoutManager = LinearLayoutManager(binding.root.context,LinearLayoutManager.VERTICAL,false)
            binding.rvSetList.setHasFixedSize(true)
            val newAdapter = SetAdapter(exercise)
            binding.rvSetList.adapter = newAdapter
        }

        private fun setButtons(binding: ListWorkoutTodoBinding, exercise: Exercise) {
            Log.d("debugshow setBtn", "$exercise")

            if (exercise.lastReps != null) setSetAdapter(binding, exercise)

            binding.btnSetAdd.setOnClickListener {
                // set를 modify 합니다.
                // 이때 modify 된 set는 운동이 완료된 후 viewModel 에 업데이트합니다.
                val dialogBinding = AddSetDialogBinding.inflate(LayoutInflater.from(binding.root.context))
                val dialog = AlertDialog.Builder(binding.root.context).run {
                    if (exercise.lastReps == null) {
                        dialogBinding.etReps.hint = ""
                        dialogBinding.etWeight.hint = ""
                    } else {
                        val setLastIndex = exercise.lastWeights?.lastIndex!!

                        val lastRepsVal = exercise.lastReps?.get(setLastIndex).toString()
                        val lastWeightVal = exercise.lastWeights?.get(setLastIndex).toString()

                        dialogBinding.etReps.hint = lastRepsVal
                        dialogBinding.etWeight.hint = lastWeightVal
                        dialogBinding.etReps.setText(lastRepsVal)
                        dialogBinding.etWeight.setText(lastWeightVal)
                    }
                    setView(dialogBinding.root)
                    setPositiveButton("추가", null)
                    setNegativeButton("취소", null)
                    show()
                }

                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {

                    var inputReps: Int? = dialogBinding.etReps.text.toString().toIntOrNull()
                    var inputWeight: Double? = dialogBinding.etWeight.text.toString().toDoubleOrNull()

                    if (inputReps == null || inputWeight == null) {
                        Toast.makeText(binding.root.context, "입력 값을 확인해주세요.", Toast.LENGTH_SHORT).show()
                    } else if (inputReps <= 0 || inputWeight <= 0.0) {
                        Toast.makeText(binding.root.context, "입력 값을 확인해주세요.", Toast.LENGTH_SHORT).show()
                    } else {
                        if (exercise.lastReps == null && exercise.lastWeights == null) {
                            listener.onSetClick(adapterPosition, CommandSymbol.ADD, inputReps, inputWeight)
                            setSetAdapter(binding, exercise)
                        } else {
                            listener.onSetClick(adapterPosition, CommandSymbol.ADD, inputReps, inputWeight)
                            binding.rvSetList.adapter?.notifyDataSetChanged()
                        }
                        dialog.dismiss()
                    }
                }
            }

            binding.btnSetRemove.setOnClickListener {
                if (exercise.lastReps == null && exercise.lastWeights == null)
                    Toast.makeText(binding.root.context, "추가된 세트가 없습니다.", Toast.LENGTH_SHORT).show()
                else if (exercise.lastReps?.size == 1 && exercise.lastWeights?.size == 1){
                    Toast.makeText(binding.root.context, "첫 번째 세트는 삭제할 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
                else {
                    listener.onSetClick(adapterPosition, CommandSymbol.REMOVE, 0, 0.0)
                    binding.rvSetList.adapter?.notifyDataSetChanged()
                }
            }
        }

        fun bind(exercise: Exercise, position: Int) {
            this.name.text = exercise.name
            this.img.setImageResource(exercise.img)

            setButtons(binding, exercise)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListWorkoutTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, myListener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val exercise = data?.get(position)
        if (exercise != null) holder.bind(exercise, position)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: MutableList<Exercise>?) {
        data?.clear()
        if (newList != null) {
            data?.addAll(newList)
        }
        this.notifyDataSetChanged()
    }
}