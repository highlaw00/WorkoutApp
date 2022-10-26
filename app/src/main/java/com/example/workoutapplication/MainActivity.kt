package com.example.workoutapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.workoutapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIntentButton.setOnClickListener{
            val intent = Intent(this, SubActivity::class.java)
            intent.putExtra("calendar data", binding.btnIntentButton.text.toString())
            startActivity(intent)
        }
    }
}