package com.contacts.mazaady.core.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.contacts.mazaady.R
import com.contacts.mazaady.databinding.LayoutLogoutDialogeBinding

class LogoutDialog(private val onAction: () -> Unit) :
    DialogFragment() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        LayoutLogoutDialogeBinding.inflate(
            layoutInflater
        )
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
        initClickListeners()
    }

    private fun initClickListeners() {

        binding.btnOk.setOnClickListener {
            onAction()
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }
}