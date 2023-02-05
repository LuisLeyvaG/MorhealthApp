package com.example.morhealth.registerfragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.morhealth.R
import com.example.morhealth.RegisterActivity
import com.example.morhealth.databinding.ActivityLoginBinding
import com.example.morhealth.databinding.FragmentUsernameBinding

class UsernameFragment : Fragmentillo() {

    private lateinit var binding: FragmentUsernameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsernameBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {

            Log.i("UsernameFragment", "Username: ${binding.etUsername.text.toString()}")
            RegisterActivity.user.username = binding.etUsername.text.toString()
            goInfoFragment()

        }
    }

    fun goInfoFragment() {
        animateFragmentTransaction(RegisterActivity.fragmentInfo)
    }
}