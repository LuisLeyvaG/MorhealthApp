package com.example.morhealth.registerfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.morhealth.R
import com.example.morhealth.RegisterActivity
import com.example.morhealth.databinding.FragmentEmailBinding
import com.example.morhealth.databinding.FragmentInfoBinding

class EmailFragment : Fragmentillo() {

    private lateinit var binding: FragmentEmailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmailBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {

            RegisterActivity.user.email = binding.etEmail.text.toString()

            goPasswordFragment()

        }
    }

    fun goPasswordFragment() {
        animateFragmentTransaction(RegisterActivity.fragmentPassword)
    }

}