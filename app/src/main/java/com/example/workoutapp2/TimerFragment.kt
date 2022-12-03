package com.example.workoutapp2

import android.app.AlertDialog
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp2.databinding.AddSetDialogBinding
import com.example.workoutapp2.databinding.FragmentTimerBinding
import com.example.workoutapp2.databinding.ListWorkoutTodoBinding
import com.example.workoutapp2.viewmodel.ExerciseViewModel
import com.example.workoutapp2.WorkoutToDoAdapter
class TimerFragment : Fragment(){

    private val viewModel: ExerciseViewModel by activityViewModels()

    var binding: FragmentTimerBinding? = null
    var running: Boolean=false
    var pauseTime =0L
    var i=0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTimerBinding.inflate(inflater)
        return binding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var listToDo: MutableList<Exercise>? = null
        var currIdx = 0

        listToDo = viewModel.todoList.value?.toMutableList()
        var currExercise = listToDo?.get(currIdx)
        currExercise?.img?.let { binding?.ivTimerWorkoutImage?.setImageResource(it) }
        binding?.tvTimerWorkoutName?.text = currExercise?.name
        binding?.tvTimerWorkoutSet?.text = "세트 ${currIdx + 1}"
        binding?.tvTimerWorkoutWeight?.text = "${currExercise?.lastWeights?.get(currIdx)} kg"
        binding?.tvTimerWorkoutReps?.text = "${currExercise?.lastReps?.get(currIdx)} 회"



        viewMode("stop")

        binding?.startBtn?.setOnClickListener {
            if (!running) {
                binding?.chronometer!!.base = SystemClock.elapsedRealtime() - pauseTime

                binding?.chronometer!!.start()

                viewMode("start")

            }
        }
        binding?.stopBtn?.setOnClickListener {
            if (running) {
                binding?.chronometer!!.stop()

                pauseTime = SystemClock.elapsedRealtime() - binding?.chronometer!!.base

                viewMode("stop")

            }
        }
        binding?.addSetBtn?.setOnClickListener {
            binding?.chronometer?.base = SystemClock.elapsedRealtime()

            pauseTime = 0L

            binding?.chronometer?.stop()

            viewMode("stop")


            var setLastIndex = currExercise?.lastWeights?.lastIndex!!
            currExercise?.img?.let { binding?.ivTimerWorkoutImage?.setImageResource(it) }

            if (currIdx== setLastIndex) {
                currIdx--

                binding?.back?.visibility = View.VISIBLE
                binding?.TheEnd?.visibility = View.VISIBLE
                binding?.startBtn?.isEnabled = false
                binding?.stopBtn?.isEnabled = false
                binding?.addSetBtn?.isEnabled=false
                binding?.TheEnd?.text="${currExercise?.name}의 \n 세트를 모두 마쳤습니다"

                binding?.tothenext?.visibility=View.VISIBLE
                binding?.tothenext?.setOnClickListener {
                    i++

                    currExercise = listToDo?.get(i)
                    setLastIndex = currExercise?.lastWeights?.lastIndex!!
                    currIdx=0
                    binding?.back?.visibility = View.INVISIBLE
                    binding?.TheEnd?.visibility = View.INVISIBLE
                    binding?.tothenext?.visibility=View.INVISIBLE
                    binding?.tvTimerWorkoutName?.text = currExercise?.name
                    binding?.tvTimerWorkoutSet?.text = "세트 ${currIdx + 1}"
                    binding?.tvTimerWorkoutWeight?.text = "${currExercise?.lastWeights?.get(currIdx)} kg"
                    binding?.tvTimerWorkoutReps?.text = "${currExercise?.lastReps?.get(currIdx)} 회"
                    binding?.startBtn?.isEnabled = true
                    binding?.stopBtn?.isEnabled = true
                    binding?.addSetBtn?.isEnabled=true
                }
            }
            currExercise = listToDo?.get(i)
            setLastIndex = currExercise?.lastWeights?.lastIndex!!
            currIdx++

            binding?.tvTimerWorkoutName?.text = currExercise?.name
            binding?.tvTimerWorkoutSet?.text = "세트 ${currIdx + 1}"
            binding?.tvTimerWorkoutWeight?.text = "${currExercise?.lastWeights?.get(currIdx)} kg"
            binding?.tvTimerWorkoutReps?.text = "${currExercise?.lastReps?.get(currIdx)} 회"
            binding?.back?.setOnClickListener {
                findNavController().navigate(R.id.action_timerFragment_to_toDoFragment)
            }
            if((i+1)==listToDo?.size && currIdx==setLastIndex){
                binding?.TheEnd?.text="수고하셨습니다.\n 설정하신 운동을 모두 끝냈습니다."
                binding?.tothenext?.isEnabled=false
            }

            val dialogBinding =
                AddSetDialogBinding.inflate(LayoutInflater.from(binding!!.root.context))
            val dialog = AlertDialog.Builder(binding!!.root.context).run {
                if (currExercise?.lastReps == null) {

                    dialogBinding.etReps.hint = ""
                    dialogBinding.etWeight.hint = ""
                } else {
                    val setLastIndex = currExercise?.lastWeights?.lastIndex!!

                    val lastRepsVal = currExercise?.lastReps?.get(setLastIndex).toString()
                    val lastWeightVal = currExercise?.lastWeights?.get(setLastIndex).toString()

                    dialogBinding.etReps.hint = lastRepsVal
                    dialogBinding.etWeight.hint = lastWeightVal

                }
                setView(dialogBinding.root)
                setPositiveButton("설정하기", null)

                show()


            }
        }

        }

        private fun viewMode(mode: String) {
            if (mode == "start") {
                binding?.startBtn?.isEnabled = false
                binding?.stopBtn?.isEnabled = true
                running = true
            } else {
                binding?.startBtn?.isEnabled = true
                binding?.stopBtn?.isEnabled = false
                binding?.addSetBtn?.isEnabled = true
                running = false
            }
        }

}




