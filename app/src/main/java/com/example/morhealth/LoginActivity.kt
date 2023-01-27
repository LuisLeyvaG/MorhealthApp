package com.example.morhealth

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.morhealth.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText

    private lateinit var binding: ActivityLoginBinding

    private var TAG: String = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_login)
        setContentView(binding.root)

        etUsername = binding.etUsername
        etPassword = binding.etPassword

        // Cambiar visibilidad de la contraseña
        binding.cbToggleVisibility.setOnCheckedChangeListener { compoundButton, isChecked ->
            val start: Int
            val end: Int
            Log.i("inside checkbox change", "" + isChecked)
            if (!isChecked) {
                start = etPassword.getSelectionStart()
                end = etPassword.getSelectionEnd()
                etPassword.setTransformationMethod(PasswordTransformationMethod())
                etPassword.setSelection(start, end)
            } else {
                start = etPassword.getSelectionStart()
                end = etPassword.getSelectionEnd()
                etPassword.setTransformationMethod(null)
                etPassword.setSelection(start, end)
            }
        }
    }

    // Ir a la pantalla principal
    fun goMain(v: View) {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun signIn(v: View) {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}