package com.example.morhealth

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.morhealth.data.ClientDAO
import com.example.morhealth.databinding.ActivityLoginBinding
import com.example.morhealth.domain.Client


class LoginActivity : AppCompatActivity() {

    companion object {
        var user: Client? = null
    }

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText

    private lateinit var binding: ActivityLoginBinding

    private lateinit var username: String
    private lateinit var password: String

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

    private fun goHome() {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }

    fun signIn(v: View) {
        username = etUsername.text.toString()
        password = etPassword.text.toString()
        val clientDAO = ClientDAO(this)

        if (clientDAO.validateLogin(username, password)) {

            Toast.makeText(this, "Bienvenido $username", Toast.LENGTH_SHORT).show()
            user = clientDAO.selectClient(username)!!
            goHome()

        } else {
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
    }
}