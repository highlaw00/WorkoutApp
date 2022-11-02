package com.example.workoutapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.example.workoutapplication.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNavi.setOnClickListener {
            binding.layoutDrawer.openDrawer(GravityCompat.START)
            // layoutDrawer를 열어라 그런데 START: Left, END: Right 로 밀어줘라
        }
        binding.naviView.setNavigationItemSelectedListener(this)
        // 네비게이션 메뉴 아이템에 클릭 속성 부여
        // 이 함수가 없는 경우 아무리 클릭해도 onNavigationItemSelected 가 작동하지 않음.

        binding.btnIntentButton.setOnClickListener{
            val intent = Intent(this, PreparationActivity::class.java)
            intent.putExtra("calendar data", binding.btnIntentButton.text.toString())
            startActivity(intent)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // 네비게이션 메뉴 아이템 클릭 시 수정 하는 함수
        when (item.itemId)
        {
            R.id.report -> Toast.makeText(applicationContext, "레포트 열람", Toast.LENGTH_SHORT).show()
            R.id.settings -> Toast.makeText(applicationContext, "환경설정", Toast.LENGTH_SHORT).show()
            R.id.help -> Toast.makeText(applicationContext, "도움말", Toast.LENGTH_SHORT).show()
        }
        binding.layoutDrawer.closeDrawers() // 네비게이션 뷰 닫는 함수
        return false
    }

    // 아래 override 함수는 네비 뷰가 열린 경우 뒤로가기 버튼을 눌렀을 때 앱이 꺼짐을 방지
    override fun onBackPressed() {
        if (binding.layoutDrawer.isDrawerOpen(GravityCompat.START)){
            binding.layoutDrawer.closeDrawers()
        }else{
            super.onBackPressed() // 일반 백버튼 기능 실행 구문
        }
    }
}