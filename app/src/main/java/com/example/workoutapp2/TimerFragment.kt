package com.example.workoutapp2

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.workoutapp2.databinding.FragmentTimerBinding
import com.example.workoutapp2.databinding.FragmentToDoBinding
import com.example.workoutapp2.viewmodel.ExerciseViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class TimerFragment : Fragment(){

    var binding: FragmentTimerBinding? = null
    var running:Boolean=false
    var pauseTime =0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerBinding.inflate(inflater)
        return binding?.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        binding?.resetBtn?.setOnClickListener {
            binding?.chronometer?.base = SystemClock.elapsedRealtime()

            pauseTime = 0L

            binding?.chronometer?.stop()

            viewMode("stop")
        }
    }
    private fun viewMode(mode:String)
    {
        if(mode=="start")
        {
            binding?.startBtn?.isEnabled=false
            binding?.stopBtn?.isEnabled=true
            binding?.resetBtn?.isEnabled=true
            running=true
        }
        else
        {
            binding?.startBtn?.isEnabled=true
            binding?.stopBtn?.isEnabled=false
            binding?.resetBtn?.isEnabled=true
            running = false
        }
    }
}




