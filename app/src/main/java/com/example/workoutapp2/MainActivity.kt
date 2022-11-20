package com.example.workoutapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.workoutapp2.databinding.ActivityMainBinding
import com.example.workoutapp2.viewmodel.ExerciseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var appBarConfiguration: AppBarConfiguration
    val viewModel: ExerciseViewModel by viewModels()

    private fun checkKey(): Boolean {
        val pref = getSharedPreferences("KEY_PREF",0)
        return pref.contains("com.example.workoutapp2.UUID_KEY")
    }

    private fun makeKey(): String {
        val pref = getSharedPreferences( "KEY_PREF",0)
        val edit = pref.edit()
        val uuid = UUID.randomUUID().toString()
        edit.putString("com.example.workoutapp2.UUID_KEY", uuid)
        edit.apply()
        return uuid
    }

    private fun getKey(): String? {
        return getSharedPreferences("KEY_PREF", 0).getString("com.example.workoutapp2.UUID_KEY", null).toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val navController = binding.frgNav.getFragment<NavHostFragment>().navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.entryFragment)
        )

        if (!checkKey()) DataBaseEntry.setUnni(makeKey())
        else DataBaseEntry.setUnni(getKey())



        setContentView(binding.root)
    }
}