package com.example.workoutapp2

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.workoutapp2.databinding.AddDialogBinding
import com.example.workoutapp2.databinding.FragmentAddBinding
import com.example.workoutapp2.viewmodel.ExerciseViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class AddFragment : Fragment() {
    var keyData: String? = null
    var binding: FragmentAddBinding? = null
    var mainColRef: CollectionReference? = null
    var customColRef: CollectionReference? = null

    private fun showResponse(response: ExerciseResponse?) {
        for (elem in response?.workouts!!) {
            Log.d("yool", elem.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            keyData = it.getString("KEY")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddBinding.inflate(inflater)
        mainColRef = FirebaseFirestore.getInstance().collection(DataBaseEntry.MAIN_DB_STRING)
        customColRef = FirebaseFirestore.getInstance().collection(DataBaseEntry.CUSTOM_DB_STRING)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: ExerciseViewModel by activityViewModels()

        // todo: tab layout 설정
        val viewPager: ViewPager2? = binding?.vpList
        val tabLayout: TabLayout? = binding?.tabAddLayout

//        viewModel.mainList.observe(viewLifecycleOwner) { mainList ->
//            // do what i want to do.
//            // 뷰 모델의 상태를 뷰에다가 쏴주는 부분
//            val viewPagerFragmentAdapter = this.activity?.let { ViewpagerFragmentAdapter(it, 1, mainList) }
//            viewPager?.adapter = viewPagerFragmentAdapter
//            val tabTitles = listOf<String>("전체", "가슴", "등", "팔", "어깨", "하체", "복근")
//            tabLayout?.let {
//                if (viewPager != null) {
//                    TabLayoutMediator(it, viewPager, { tab, position -> tab.text = tabTitles[position]}).attach()
//                }
//            }
//        }

        // adapter 에 notify 해줄때 사용함수
        // adapter.nofityDataSetChanged()

        // observe 안에서 adapter 바꾸지 말 것

        // observe AddFragment 가 아닌 AddListFragment 에서 진행

        val viewPagerFragmentAdapter = this.activity?.let { ViewpagerFragmentAdapter(it, 1) }
        viewPager?.adapter = viewPagerFragmentAdapter
        val tabTitles = listOf<String>("전체", "가슴", "등", "팔", "어깨", "하체", "복근")
        tabLayout?.let {
            if (viewPager != null) {
                TabLayoutMediator(it, viewPager, { tab, position -> tab.text = tabTitles[position]}).attach()
            }
        }



        // todo: addToList Button 설정
        binding?.btnAddToTodo?.setOnClickListener {
        }

        // todo: addToDb button 설정
        // todo: 새로운 운동을 추가할 때, wholeList에 존재한다면 추가할 수 없도록함.
        binding?.btnAddToDb?.setOnClickListener {
            val dialogBinding = AddDialogBinding.inflate(layoutInflater)
            val dialog = this.context?.let { it1 ->
                AlertDialog.Builder(it1).run {
                    setView(dialogBinding.root)
                    setPositiveButton("추가", null)
                    setNegativeButton("취소", null)
                    show()
                }
            }
            dialog?.getButton(DialogInterface.BUTTON_POSITIVE)?.setOnClickListener {
                val name = dialogBinding.etName.text.toString()
                val partId = dialogBinding.rgPart.checkedRadioButtonId
                if (name.isNotBlank() and (partId != -1)){
                    val partString = when (dialogBinding.rgPart.findViewById<RadioButton>(partId).text) {
                        "가슴" -> "Chest"
                        "등" -> "Back"
                        "팔" -> "Arm"
                        "어깨" -> "Delts"
                        "하체" -> "Legs"
                        "복근" -> "Abs"
                        else -> "Undefined"
                    }
                    val newExercise = Exercise(name = name, part = partString)
                    Log.d("debugshow addfragment", "wholeList: ${viewModel.wholeList.value ?: "empty"}")
                    if (viewModel.isValid(newExercise)) {
                        viewModel.addToCustom(newExercise)
                        dialog?.dismiss()
                    }else {
                        Toast.makeText(this.context, "이미 존재하는 운동입니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this.context, "운동의 이름과 부위를 선택해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}