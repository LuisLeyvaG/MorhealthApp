package com.example.morhealth.homefragments

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.morhealth.HomeActivity
import com.example.morhealth.api.FitnessApiManager
import com.example.morhealth.databinding.FragmentHealthBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.material.appbar.AppBarLayout
import java.util.concurrent.TimeUnit


class HealthFragment : Fragment() {

    private lateinit var binding: FragmentHealthBinding

    private val REQUEST_OAUTH_REQUEST_CODE = 1

    private lateinit var fitnessApiManager: FitnessApiManager

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

        fitnessApiManager = FitnessApiManager.getInstance(requireContext())

        fitnessApiManager = FitnessApiManager.getInstance(requireContext())

        if (!GoogleSignIn.hasPermissions(
                GoogleSignIn.getLastSignedInAccount(requireContext()),
                Fitness.SCOPE_ACTIVITY_READ_WRITE
            )
        ) {

            GoogleSignIn.requestPermissions(
                requireActivity(),
                REQUEST_OAUTH_REQUEST_CODE,
                GoogleSignIn.getLastSignedInAccount(requireContext()),
                Fitness.SCOPE_ACTIVITY_READ_WRITE
            )
        } else {
            // El usuario ha otorgado los permisos necesarios, puedes utilizar los métodos de la API de fitness aquí
            initToolbarListener()
            validateToolbar()

            /*val stepCount = fitnessApiManager.getStepCount(
                System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7),
                System.currentTimeMillis()
            )
            Log.d("MainActivity", "Step count for last 7 days: $stepCount")*/
        }

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

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // El usuario ha otorgado los permisos necesarios, puedes utilizar los métodos de la API de fitness aquí
                val stepCount = fitnessApiManager.getStepCount(
                    System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7),
                    System.currentTimeMillis()
                )
                Log.d("HealthFragment", "Step count for last 7 days: $stepCount")
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // El usuario no ha otorgado los permisos necesarios, muestra un mensaje de error
                Toast.makeText(
                    requireContext(),
                    "Permisos de Google Fit requeridos",
                    Toast.LENGTH_SHORT
                ).show()
                // Solo llama a goHomeFragment si el usuario cancela la solicitud de permisos
                goHomeFragment()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun goHomeFragment() {
        (activity as HomeActivity).setFragment(HomeActivity.homeFragment)
    }
}