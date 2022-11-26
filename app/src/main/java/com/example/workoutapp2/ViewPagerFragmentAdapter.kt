package com.example.workoutapp2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.workoutapp2.viewmodel.ExerciseViewModel

class ViewpagerFragmentAdapter(parentFragment: Fragment/*fragmentActivity: FragmentActivity*/, id: Int) : /*FragmentStateAdapter(fragmentActivity)*/ FragmentStateAdapter(parentFragment){
    val fragmentList = when (id) {
        0 -> {
            listOf(
                WorkoutFragment(),
                DietFragment()
            )
        }
        1 -> {
            listOf<Fragment>(
                AddListFragment("All"),
                AddListFragment("Chest"),
                AddListFragment("Back"),
                AddListFragment("Arm"),
                AddListFragment("Delts"),
                AddListFragment("Legs"),
                AddListFragment("Abs"))
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