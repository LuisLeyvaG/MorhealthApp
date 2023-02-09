package com.example.morhealth.homefragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import com.example.morhealth.HomeActivity
import com.example.morhealth.R
import com.example.morhealth.databinding.FragmentHealthBinding
import com.example.morhealth.databinding.FragmentHomeBinding
import com.google.android.material.appbar.AppBarLayout

class HealthFragment : Fragment() {

    private lateinit var binding: FragmentHealthBinding

    private lateinit var appBarLayout: AppBarLayout
    private lateinit var scrollView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHealthBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbarListener()
        validateToolbar()

    }

    private fun initToolbarListener() {
        appBarLayout = (activity as HomeActivity).appBarLayout
        scrollView = binding.scrollViewHealth

        scrollView.viewTreeObserver.addOnScrollChangedListener {
            validateToolbar()
        }
    }

    private fun validateToolbar() {
        if (scrollView.scrollY > 70) {
            appBarLayout.setExpanded(false, false)
        } else {
            appBarLayout.setExpanded(true, true)
        }
    }
}