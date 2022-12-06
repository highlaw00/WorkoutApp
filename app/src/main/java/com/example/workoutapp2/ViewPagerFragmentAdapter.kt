package com.example.workoutapp2

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewpagerFragmentAdapter(parentFragment: Fragment) : FragmentStateAdapter(parentFragment){
    private val fragmentList = listOf<Fragment>(
                PartListFragment("All"),
                PartListFragment("Chest"),
                PartListFragment("Back"),
                PartListFragment("Arm"),
                PartListFragment("Delts"),
                PartListFragment("Legs"),
                PartListFragment("Abs"))


    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}