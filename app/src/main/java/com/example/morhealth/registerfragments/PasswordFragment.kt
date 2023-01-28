package com.example.morhealth.registerfragments

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.morhealth.LoginActivity
import com.example.morhealth.MainActivity
import com.example.morhealth.R
import com.example.morhealth.RegisterActivity
import com.example.morhealth.data.ClientDAO
import com.example.morhealth.databinding.FragmentEmailBinding
import com.example.morhealth.databinding.FragmentPasswordBinding

class PasswordFragment : Fragmentillo() {

    private lateinit var binding: FragmentPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPasswordBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etPassword = binding.etPassword

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

        binding.btnNext.setOnClickListener {

            RegisterActivity.user.pswd = binding.etPassword.text.toString()
            val act = activity as RegisterActivity
            act.signUp()

        }
    }



}