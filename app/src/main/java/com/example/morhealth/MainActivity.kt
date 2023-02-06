package com.example.morhealth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.morhealth.data.ClientDAO

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun AppCompatActivity.hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    fun Fragment.hideKeyboard() {
        val activity = this.activity
        if (activity is AppCompatActivity) {
            activity.hideKeyboard()
        }
    }

    fun goLogin(v: View) {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    fun goRegister(v: View) {
        val intent = Intent(this@MainActivity, RegisterActivity::class.java)
        startActivity(intent)
    }
}