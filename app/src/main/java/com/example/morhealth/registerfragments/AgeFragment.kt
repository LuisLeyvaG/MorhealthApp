package com.example.morhealth.registerfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import com.example.morhealth.R
import com.example.morhealth.RegisterActivity
import com.example.morhealth.databinding.FragmentAgeBinding
import com.example.morhealth.databinding.FragmentGenderBinding

class AgeFragment : Fragmentillo() {

    private lateinit var binding: FragmentAgeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAgeBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val np = binding.npAge

        np.minValue = 0
        np.maxValue = 100
        np.value = 17
        np.wrapSelectorWheel = true

        binding.btnNext.setOnClickListener {

            RegisterActivity.user.age = np.value
            goEmailFragment()

        }
    }

    fun goEmailFragment() {
        animateFragmentTransaction(RegisterActivity.fragmentEmail)
    }
}