package com.example.morhealth.registerfragments

import androidx.fragment.app.Fragment
import com.example.morhealth.R

open class Fragmentillo: Fragment() {
    protected fun animateFragmentTransaction(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
        transaction?.replace(R.id.fragmentContainer, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
}