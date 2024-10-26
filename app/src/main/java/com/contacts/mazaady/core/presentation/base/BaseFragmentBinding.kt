package com.contacts.mazaady.core.presentation.base

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.contacts.mazaady.R
import com.contacts.mazaady.databinding.FragmentBaseBinding
import com.contacts.mazaady.core.presentation.extensions.hideKeyboard
import com.facebook.shimmer.ShimmerFrameLayout
import es.dmoral.toasty.Toasty
import com.contacts.mazaady.core.presentation.dialog.ViewDialog
import java.lang.reflect.ParameterizedType

abstract class BaseFragmentBinding<VB : ViewBinding> : Fragment() {

    val binding by lazy { initBinding() }
    private val baseBinding by lazy { FragmentBaseBinding.inflate(layoutInflater) }
    private val loadingDialog by lazy { activity?.let { ViewDialog(it) } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        baseBinding.flContainer.apply {
            removeAllViews()
            addView(binding.root)
        }
        dismissLoading()
        return baseBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("SCREEN_NAME", "Screen full path: ${javaClass.name}")
    }

    override fun onDestroy() {
        // Dismiss dialog before fragment destroyed otherwise IllegalArgumentException will arise.
        dismissLoading()
        super.onDestroy()
    }

    /**
     * Show loading dialog
     */
    fun showLoading() {
        loadingDialog?.showDialog()
    }

    /**
     * Dismiss loading dialog
     */
    fun dismissLoading() {
        // Make sure that fragment is alive otherwise IllegalArgumentException will arise.
        if (isDetached.not()) loadingDialog?.hideDialog()
    }

    /**
     * change status bar for the screen
     */
    @SuppressLint("ObsoleteSdkInt")
    fun Fragment.setStatusBarColor(statusBarColor: Int, iconStatusBarColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = requireActivity().window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(requireContext(), statusBarColor)

            // Set status bar icon color
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val decorView = window.decorView
                var flags = decorView.systemUiVisibility
                flags = if (iconStatusBarColor == R.color.white) {
                    flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                } else {
                    flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
                decorView.systemUiVisibility = flags
            }
        }
    }
    /**
     * Show Toast
     */
    fun generalToast(message: String) {
        makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun successToast(message: String) {
        Toasty.success(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun errorToast(message: String) {
        Toasty.error(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun animateUp(view: View) {
        val animation = android.view.animation.AnimationUtils.loadAnimation(
            requireActivity(),
            R.anim.slide_anim_up_slow
        )
        view.startAnimation(animation)
    }

    fun animateDown(view: View) {
        val animation = android.view.animation.AnimationUtils.loadAnimation(
            requireActivity(),
            R.anim.slide_anim_down_slow
        )
        view.startAnimation(animation)
    }

    fun animateIn(view: View) {
        val animation = android.view.animation.AnimationUtils.loadAnimation(
            requireActivity(),
            R.anim.fade_out
        )
        view.startAnimation(animation)
    }

    /**
     * start Shimmer
     */
    fun startShimmer(shimmerView: ShimmerFrameLayout, view: View) {
        shimmerView.isVisible = true
        view.isVisible = false
        shimmerView.startShimmerAnimation()
    }

    /**
     * stop Shimmer
     */
    fun stopShimmer(shimmerView: ShimmerFrameLayout, view: View) {
        shimmerView.isVisible = false
        view.isVisible = true
        shimmerView.stopShimmerAnimation()
    }


    @Suppress("UNCHECKED_CAST")
    private fun initBinding(): VB {
        val superclass = javaClass.genericSuperclass as ParameterizedType
        val method = (superclass.actualTypeArguments[0] as Class<Any>)
            .getDeclaredMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, layoutInflater) as VB
    }


    fun <T : Any> setBackStackResult(key: String, data: T, doBack: Boolean = true) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, data)
        if (doBack) {
            findNavController().popBackStack()
        }
    }

    fun <T : Any> getBackStackResult(key: String, result: (T) -> (Unit)) {
        val observer =
            findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)
        observer?.observe(viewLifecycleOwner) {
            it?.let {
                result(it)
            }
        }
        observer?.postValue(null)
    }

    override fun onDestroyView() {
        view?.hideKeyboard()
        loadingDialog?.hideDialog()
        super.onDestroyView()
//        _binding = null
    }

    fun openAppSettings() {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.fromParts("package", requireActivity().packageName, null)
            startActivity(this)
        }
    }
}