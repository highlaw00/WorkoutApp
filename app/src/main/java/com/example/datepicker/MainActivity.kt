package com.example.datepicker

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {

     @RequiresApi(Build.VERSION_CODES.O)
     @SuppressLint("SetTextl18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         val dayText:TextView= findViewById(R.id.day_text)
         val datePicker: DatePicker= findViewById((R.id.datePicker))

         val iYear: Int= datePicker.year
         val iMonth: Int= datePicker .month+1
         val iDay: Int= datePicker. dayOfMonth

         dayText.text="${iYear}년 ${iMonth} 월 ${iDay} 일"

         datePicker. setOnDateChangedListener{datePicker, year, month, dayOfMonth ->
             dayText.text= "${year}년 ${month+1} 월 ${dayOfMonth} 일"
         }

    }

}