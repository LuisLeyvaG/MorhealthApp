package com.example.morhealth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.morhealth.databinding.ActivityHomeBinding
import com.example.morhealth.databinding.ActivityLoginBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}