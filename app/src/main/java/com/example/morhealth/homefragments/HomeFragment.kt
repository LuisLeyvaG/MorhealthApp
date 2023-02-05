package com.example.morhealth.homefragments

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.morhealth.R
import com.example.morhealth.databinding.FragmentHomeBinding
import com.example.morhealth.databinding.FragmentUsernameBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

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
    }
}