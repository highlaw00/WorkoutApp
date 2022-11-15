package com.example.workoutapp2

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.example.workoutapp2.databinding.AddDialogBinding
import com.example.workoutapp2.databinding.FragmentAddBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class AddFragment : Fragment() {
    var keyData: String? = null
    var binding: FragmentAddBinding? = null
    var mainColRef: CollectionReference? = null
    var customColRef: CollectionReference? = null

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
        // todo: tab layout 설정
        val viewPager: ViewPager2? = binding?.vpList
        val tabLayout: TabLayout? = binding?.tabAddLayout

        val viewPagerFragmentAdapter = this.activity?.let { ViewpagerFragmentAdapter(it, 1) }
        viewPager?.adapter = viewPagerFragmentAdapter

        val tabTitles = listOf<String>("전체", "가슴", "등", "팔", "어깨", "하체", "복근")
        tabLayout?.let {
            if (viewPager != null) {
                TabLayoutMediator(it, viewPager, { tab, position -> tab.text = tabTitles[position]}).attach()
            }
        }

        // todo: addToDb button 설정
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
                val customUniqueColRef = customColRef?.document(DataBaseEntry.UNNI_KEY ?: "Error with key while Add")
                    ?.collection(DataBaseEntry.CUSTOM_WORKOUT_DIRECTORY_STRING)
                if (name.isNotBlank() and (partId != -1)){
                    val partString = when (dialogBinding.root.findViewById<RadioButton>(partId).text) {
                        "가슴" -> "Chest"
                        "등" -> "Back"
                        "팔" -> "Arm"
                        "어깨" -> "Delts"
                        "하체" -> "Legs"
                        "복근" -> "Abs"
                        else -> "Undefined"
                    }
                    val newExercise = Exercise(name = name, part = partString)
                    customUniqueColRef?.document(name)?.set(newExercise)
                    dialog?.dismiss()
                } else {
                    Toast.makeText(this.context, "운동의 이름과 부위를 선택해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}