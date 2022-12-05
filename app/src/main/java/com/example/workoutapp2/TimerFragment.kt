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
    var count=0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTimerBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var listToDo: MutableList<Exercise>? = null
        var currIdx = 0

        listToDo = viewModel.todoList.value?.toMutableList()
        var currExercise = listToDo?.get(currIdx)

        fun getInformation(currExercise: Exercise?, currIdx:Int){
            binding?.tvTimerWorkoutName?.text = currExercise?.name
            binding?.tvTimerWorkoutSet?.text = "세트 ${currIdx + 1}"
            binding?.tvTimerWorkoutWeight?.text = "${currExercise?.lastWeights?.get(currIdx)} kg"
            binding?.tvTimerWorkoutReps?.text = "${currExercise?.lastReps?.get(currIdx)} 회"
        }

        currExercise?.img?.let { binding?.ivTimerWorkoutImage?.setImageResource(it) }
        getInformation(currExercise,currIdx)



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
                binding?.tothenext?.visibility=View.VISIBLE
                binding?.startBtn?.isEnabled = false
                binding?.stopBtn?.isEnabled = false
                binding?.addSetBtn?.isEnabled=false
                binding?.TheEnd?.text="${currExercise?.name}의\n세트를 모두 마쳤습니다"


                binding?.tothenext?.setOnClickListener {
                    i++

                    currExercise = listToDo?.get(i)
                    setLastIndex = currExercise?.lastWeights?.lastIndex!!
                    currIdx=0
                    binding?.back?.visibility = View.INVISIBLE
                    binding?.TheEnd?.visibility = View.INVISIBLE
                    binding?.tothenext?.visibility=View.INVISIBLE
                    getInformation(currExercise,currIdx)
                    binding?.startBtn?.isEnabled = true
                    binding?.stopBtn?.isEnabled = true
                    binding?.addSetBtn?.isEnabled=true
                    count=0
                }
            }
            currExercise = listToDo?.get(i)
            setLastIndex = currExercise?.lastWeights?.lastIndex!!
            currIdx++

            getInformation(currExercise,currIdx)
            binding?.back?.setOnClickListener {
                findNavController().navigate(R.id.action_timerFragment_to_toDoFragment)
            }
            if((i+1)==listToDo?.size && currIdx==setLastIndex){
                binding?.TheEnd?.text="수고하셨습니다.\n 설정하신 운동을 모두 끝냈습니다."
                binding?.tothenext?.isEnabled=false
            }

            val dialogBinding =
                AddSetDialogBinding.inflate(LayoutInflater.from(binding?.root?.context))
            val dialog = AlertDialog.Builder(binding?.root?.context).run {
                val setLastIndex = currExercise?.lastWeights?.lastIndex!!
                var lastRepsVal:String
                var lastWeightVal:String
                    lastRepsVal = currExercise?.lastReps?.get(currIdx-1).toString()
                    lastWeightVal = currExercise?.lastWeights?.get(currIdx-1).toString()
                    setTitle("세트 ${currIdx} 결과")
                    if(currIdx==setLastIndex)
                    {
                        lastRepsVal = currExercise?.lastReps?.get(currIdx).toString()
                        lastWeightVal = currExercise?.lastWeights?.get(currIdx).toString()
                        setTitle("세트 ${currIdx + 1} 결과")
                        count++
                        if(currIdx==setLastIndex&&count==1)
                        {
                            lastRepsVal = currExercise?.lastReps?.get(currIdx-1).toString()
                            lastWeightVal = currExercise?.lastWeights?.get(currIdx-1).toString()
                            setTitle("세트 ${currIdx} 결과")
                        }




                }

                dialogBinding.etReps.hint = lastRepsVal
                dialogBinding.etWeight.hint = lastWeightVal

                dialogBinding.etReps.setText(lastRepsVal)
                dialogBinding.etWeight.setText(lastWeightVal)


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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}




