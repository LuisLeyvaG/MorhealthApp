package com.example.morhealth.registerfragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.morhealth.R
import com.example.morhealth.RegisterActivity
import com.example.morhealth.databinding.FragmentInfoBinding
import com.example.morhealth.databinding.FragmentUsernameBinding

class InfoFragment : Fragmentillo() {

    private lateinit var binding: FragmentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {

            RegisterActivity.user.apply {
                name = binding.etName.text.toString()
                lastname_p = binding.etLastnameP.text.toString()
                lastname_m = binding.etLastnameM.text.toString()
            }

            goGenderFragment()

        }
    }

    fun goGenderFragment() {
        animateFragmentTransaction(RegisterActivity.fragmentGender)
    }


}