package com.example.morhealth.homefragments

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import com.example.morhealth.HomeActivity
import com.example.morhealth.LoginActivity
import com.example.morhealth.R
import com.example.morhealth.databinding.FragmentHomeBinding
import com.example.morhealth.databinding.FragmentUsernameBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private var dayRoutineExpanded: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBackgroundBlob(resources.getDrawable(R.drawable.home_gradient_blob))
        binding.tvWelcomeUserName.text = LoginActivity.user!!.username

        initToolbarListener()
        //initHealthSection()
        initFitnessSection()

    }

    private fun initToolbarListener() {
        val appBarLayout = (activity as HomeActivity).appBarLayout
        val scrollView = binding.scrollViewHome

        scrollView.viewTreeObserver.addOnScrollChangedListener {
            if (scrollView.scrollY > 70) {
                appBarLayout.setExpanded(false, false)
            } else {
                appBarLayout.setExpanded(true, true)
            }
        }
    }

    private fun initFitnessSection() {
        // Expandir la rutina del día (reemplazado por botón de play)
        /*binding.btnExpandDayRoutine.setOnClickListener {
            dayRoutineExpanded = if (dayRoutineExpanded) {
                binding.btnExpandDayRoutine.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate_expand_less));
                false;
            } else {
                binding.btnExpandDayRoutine.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate_expand));
                true;
            }
        }*/
    }

    private fun setBackgroundBlob(drawable: Drawable) {
        val view = binding.vHomeBackground
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels

        drawable.alpha = 153 // 60% de transparencia
        view.background = drawable

        val params = view.layoutParams
        params.width = (screenWidth * 1.5).toInt()
        params.height = screenWidth
        view.layoutParams = params

        view.translationX = (view.layoutParams.width * 0.4).toFloat()
        view.translationY = -(view.layoutParams.height * 0.3).toFloat()
        view.translationZ = -1f

        // Animación de traslación
        val translateAnimation = TranslateAnimation(0f, 20f, 0f, 40f)
        translateAnimation.duration = 1000 // Duración de la animación en milisegundos
        translateAnimation.repeatCount = Animation.INFINITE // Repetición infinita
        translateAnimation.repeatMode = Animation.REVERSE // Modo de repetición en reversa
        translateAnimation.interpolator = AccelerateDecelerateInterpolator() // Aceleración suave
        view.startAnimation(translateAnimation)

    }
}