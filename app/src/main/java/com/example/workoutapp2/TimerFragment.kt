package com.example.workoutapp2

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.Dialog

import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
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

    lateinit var mainActivity: MainActivity
    override fun onAttach(context: Context) { //TImerFragment의 context를 가져오는 함수
        super.onAttach(context)
        mainActivity=context as MainActivity
    }
    private val viewModel: ExerciseViewModel by activityViewModels()

    var binding: FragmentTimerBinding? = null
    var running: Boolean=false
    var pauseTime =0L
    var exercise_count=0 //현재 운동의 인덱스

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTimerBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var listToDo: MutableList<Exercise>? = null
        var currIdx = 0 //현재 세트의 인덱스

        listToDo = viewModel.todoList.value?.toMutableList()
        var currExercise = listToDo?.get(currIdx) //처음 운동에 대한 정보를 가져옴
        var setLastIndex = currExercise?.lastWeights?.lastIndex!! //처음 운동의 마지막세트

        fun getInformation(currExercise: Exercise?, currIdx:Int){ //운동 정보 저장 함수
            binding?.tvTimerWorkoutName?.text = currExercise?.name
            binding?.tvTimerWorkoutSet?.text = "세트 ${currIdx + 1}"
            binding?.tvTimerWorkoutWeight?.text = "${currExercise?.lastWeights?.get(currIdx)} kg"
            binding?.tvTimerWorkoutReps?.text = "${currExercise?.lastReps?.get(currIdx)} 회"
            currExercise?.img?.let { binding?.ivTimerWorkoutImage?.setImageResource(it) }
        }

        fun ViewAndEnabled(visible1:Int,visible2:Int,visible3:Int,Enabled1:Boolean,Enabled2:Boolean,Enabled3:Boolean)
        { //  버튼 활성화 및 visible 함수
            binding?.back?.visibility = visible1
            binding?.TheEnd?.visibility = visible2
            binding?.tothenext?.visibility= visible3
            binding?.startBtn?.isEnabled = Enabled1
            binding?.stopBtn?.isEnabled = Enabled2
            binding?.addSetBtn?.isEnabled=Enabled3
        }

        getInformation(currExercise,currIdx) //처음 운동의 0번째 세트 정보로 저장

        viewMode("stop")

//        val intent= Intent(mainActivity, AlarmReceiver::class.java)
//        val pendingIntent=PendingIntent.getBroadcast(
//            mainActivity,0, intent, PendingIntent.FLAG_IMMUTABLE
//        )

        binding?.startBtn?.setOnClickListener { // 휴식 시작 버튼
            if (!running) {
                binding?.chronometer!!.base = SystemClock.elapsedRealtime() - pauseTime
                binding?.chronometer!!.start()
                viewMode("start")
            }


        }
        binding?.stopBtn?.setOnClickListener { // 휴식 중지 버튼
            if (running) {
                binding?.chronometer!!.stop()

                pauseTime = SystemClock.elapsedRealtime() - binding?.chronometer!!.base

                viewMode("stop")

            }
        }
        binding?.addSetBtn?.setOnClickListener { // 다음운동 버튼
            binding?.chronometer?.base = SystemClock.elapsedRealtime()

            pauseTime = 0L

            binding?.chronometer?.stop()

            viewMode("stop")

            currExercise?.img?.let { binding?.ivTimerWorkoutImage?.setImageResource(it) }

            binding?.let { binding ->
                currExercise?. let { exercise ->
                    this.makeDialog( binding, exercise, currIdx, currIdx == setLastIndex )
                }
            }

            if (currIdx== setLastIndex) { //현재 세트가 마지막 세트일 경우에
                currIdx--

                ViewAndEnabled(View.VISIBLE,View.VISIBLE,View.VISIBLE,false,false,false)
                binding?.TheEnd?.text="${currExercise?.name}의\n세트를 모두 마쳤습니다"

                binding?.tothenext?.setOnClickListener {
                    exercise_count++ //운동의 인덱스를 한칸 올림

                    currExercise = listToDo?.get(exercise_count) //다음 운동의 정보를 받아옴
                    setLastIndex = currExercise?.lastWeights?.lastIndex!! // 그 운동에 대한 마지막세트수를 저장
                    currIdx=0 //다음 운동으로 넘어갔을때 현재 세트값 초기화


                    ViewAndEnabled(View.INVISIBLE,View.INVISIBLE,View.INVISIBLE,true,true,true)
                    getInformation(currExercise,currIdx) // 다음세트 0번쩨 정보 가져오기
                }
                binding?.back?.setOnClickListener { //처음으로 버튼
                    findNavController().navigate(R.id.action_timerFragment_to_toDoFragment)
                }
            }

            currIdx++ //현재 세트 올리기

            getInformation(currExercise,currIdx) //운동정보 저장

            if((exercise_count+1)==listToDo?.size && currIdx==setLastIndex){ //마지막 운동을 끝내고 마지막 세트도 끝냈을때
                binding?.TheEnd?.text="수고하셨습니다.\n 설정하신 운동을 모두 끝냈습니다."
                binding?.tothenext?.isEnabled=false
            }
        }

    }

    private fun makeDialog(binding: FragmentTimerBinding, exercise: Exercise, currentSetIdx: Int, isLastSet: Boolean) {
        val dialogBinding = AddSetDialogBinding.inflate(LayoutInflater.from(binding.root.context))
        val dialog = AlertDialog.Builder(binding.root.context).run {

            val repsVal = exercise.lastReps?.get(currentSetIdx).toString()
            val weightVal = exercise.lastWeights?.get(currentSetIdx).toString()

            dialogBinding.etReps.setText(repsVal)
            dialogBinding.etWeight.setText(weightVal)

            setView(dialogBinding.root)
            setPositiveButton("확인", null)
            show()
        }
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val inputReps: Int? = dialogBinding.etReps.text.toString().toIntOrNull()
            val inputWeight: Double? = dialogBinding.etWeight.text.toString().toDoubleOrNull()

            if (inputReps == null || inputWeight == null) {
                Toast.makeText(binding.root.context, "입력 값을 확인해주세요.", Toast.LENGTH_SHORT).show()
            } else if (inputReps <= 0 || inputWeight <= 0.0) {
                Toast.makeText(binding.root.context, "입력 값을 확인해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                exercise.lastReps?.set(currentSetIdx, inputReps)
                exercise.lastWeights?.set(currentSetIdx, inputWeight)

                if (isLastSet) {
                    viewModel.updateSetByExercise(exercise)
                }

                dialog.dismiss()
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