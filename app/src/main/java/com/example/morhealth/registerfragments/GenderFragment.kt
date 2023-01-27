package com.example.morhealth.registerfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.morhealth.R
import com.example.morhealth.RegisterActivity
import com.example.morhealth.databinding.FragmentGenderBinding
import com.example.morhealth.databinding.FragmentInfoBinding

class GenderFragment : Fragmentillo() {

    private lateinit var binding: FragmentGenderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenderBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {

            when (binding.rgGender.checkedRadioButtonId) {
                R.id.rbMale -> RegisterActivity.user.gender = 'M'
                else -> RegisterActivity.user.gender = 'F'
            }

            goAgeFragment()

        }
    }

    fun goAgeFragment() {
        animateFragmentTransaction(RegisterActivity.fragmentAge)
    }


}