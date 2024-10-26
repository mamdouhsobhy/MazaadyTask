package com.contacts.mazaady.authentication.presentation.view.splash

import android.content.Context
import android.os.Bundle
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.navigation.fragment.findNavController
import com.contacts.mazaady.R
import com.contacts.mazaady.core.presentation.base.BaseFragmentBinding
import com.contacts.mazaady.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSplash : BaseFragmentBinding<FragmentSplashBinding>() {

    var cartMovedCompleted = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moveTheCart()

    }

    private fun moveTheCart() {
        val display: Display =
            (activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val screenWidth = display.width.toFloat()
        val moveDistance = screenWidth

        val animation = TranslateAnimation(0f, moveDistance, 0f, 0f)
        animation.duration = 5000

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                // Move the image back to the start position after the current animation ends
                cartMovedCompleted++
                if (cartMovedCompleted == 2 ) {
                    findNavController().popBackStack()
                    findNavController().navigate(R.id.fragmentCategory)
                } else {
                    binding.imgCart.startAnimation(animation)
                }
            }
        })

        binding.imgCart.startAnimation(animation)
    }

}