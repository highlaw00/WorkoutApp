package com.example.workoutapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.workoutapp2.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var appBarConfiguration: AppBarConfiguration

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
            setOf(R.id.startFragment)
        )

        if (!checkKey()) DataBaseEntry.setUnni(makeKey())
        else DataBaseEntry.setUnni(getKey())


        setContentView(binding.root)
    }
}