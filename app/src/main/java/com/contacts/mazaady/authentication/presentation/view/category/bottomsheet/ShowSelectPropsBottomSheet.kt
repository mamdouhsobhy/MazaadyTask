package com.contacts.mazaady.authentication.presentation.view.category.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.contacts.mazaady.R
import com.contacts.mazaady.authentication.presentation.view.category.adapter.AdapterShowSelectedProps
import com.contacts.mazaady.authentication.presentation.view.category.model.ModelSelectedProps
import com.contacts.mazaady.databinding.LayoutShowSelectedPropsDialogeBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class ShowSelectPropsBottomSheet(
    private val props:List<ModelSelectedProps>,
    private val goToHome:()->Unit
) : DialogFragment() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        LayoutShowSelectedPropsDialogeBinding.inflate(
            layoutInflater
        )
    }

    private val adapterShowSelectedProps by lazy {
        AdapterShowSelectedProps()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpCategories()
        addListenerOnView()

    }

    private fun addListenerOnView() {
        binding.btnOk.setOnClickListener {
            dismiss()
        }

        binding.btnGoToHome.setOnClickListener {
            goToHome()
            dismiss()
        }
    }

    private fun setUpCategories() {
        binding.rvSelector.adapter = adapterShowSelectedProps
        adapterShowSelectedProps.submitList(props)
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }
}