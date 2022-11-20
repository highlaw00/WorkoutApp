package com.example.workoutapp2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.workoutapp2.viewmodel.ExerciseViewModel

class ViewpagerFragmentAdapter(fragmentActivity: FragmentActivity, id: Int, list: List<Exercise>) : FragmentStateAdapter(fragmentActivity) {
    val fragmentList = when (id) {
        0 -> {
            listOf<Fragment>(
                WorkoutFragment(),
                DietFragment()
            )
        }
        1 -> {
            listOf<Fragment>(
                AddListFragment("All", list),
                AddListFragment("Chest", list),
                AddListFragment("Back", list),
                AddListFragment("Arm", list),
                AddListFragment("Delts", list),
                AddListFragment("Legs", list),
                AddListFragment("Abs", list))
        }
        else -> null

}
    override fun getItemCount(): Int {
        return fragmentList?.size ?: 0
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList?.get(position) ?: WorkoutFragment()
    }

}