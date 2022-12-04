package com.example.workoutapp2

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewpagerFragmentAdapter(parentFragment: Fragment) : FragmentStateAdapter(parentFragment){
    private val fragmentList = listOf<Fragment>(
                AddListFragment("All"),
                AddListFragment("Chest"),
                AddListFragment("Back"),
                AddListFragment("Arm"),
                AddListFragment("Delts"),
                AddListFragment("Legs"),
                AddListFragment("Abs"))


    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}