package com.example.workoutapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.workoutapp2.databinding.ActivityMainBinding
import java.util.*

/**
 * MainActivity.kt
 * 1-1. 앱을 처음 사용한다면...
 *      Android Device 별 Unique Id(UUID::class.java) 를 SharedPreference 로 저장합니다.
 *
 * 1-2. 앱을 사용한 적이 있다면...
 *      저장한 UUID 를 Data 클래스인 DataBaseEntry 의 프로퍼티에 저장합니다.
 *
 * **/

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
            setOf(R.id.entryFragment,R.id.startFragment)
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        if (!checkKey()) DataBaseStorage.setUnni(makeKey())
        else DataBaseStorage.setUnni(getKey())

        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = binding.frgNav.getFragment<NavHostFragment>().navController
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}