package com.example.morhealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goLogin(v: View) {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun goRegister(v: View) {
        val intent = Intent(this@MainActivity, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}