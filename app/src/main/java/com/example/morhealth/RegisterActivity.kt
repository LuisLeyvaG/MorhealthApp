package com.example.morhealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.morhealth.domain.Client
import com.example.morhealth.registerfragments.*

class RegisterActivity : AppCompatActivity() {

    val context = this

    companion object {

        lateinit var fragmentUsername: UsernameFragment
        lateinit var fragmentInfo: InfoFragment
        lateinit var fragmentGender: GenderFragment
        lateinit var fragmentAge: AgeFragment
        lateinit var fragmentEmail: EmailFragment
        lateinit var fragmentPassword: PasswordFragment

        lateinit var user: Client
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        user = Client()

        fragmentUsername = UsernameFragment()
        fragmentInfo = InfoFragment()
        fragmentGender = GenderFragment()
        fragmentAge = AgeFragment()
        fragmentEmail = EmailFragment()
        fragmentPassword = PasswordFragment()

        goUsernameFragment()

    }

    fun goMain(v: View) {
        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun goUsernameFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragmentUsername)
        transaction.commit()
    }
}