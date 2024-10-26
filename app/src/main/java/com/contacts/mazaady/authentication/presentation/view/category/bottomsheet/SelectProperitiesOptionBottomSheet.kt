package com.contacts.mazaady.authentication.presentation.view.category.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.contacts.mazaady.R
import com.contacts.mazaady.authentication.data.responseremote.properities.Option
import com.contacts.mazaady.authentication.presentation.view.category.adapter.AdapterProperitiesOption
import com.contacts.mazaady.core.presentation.extensions.onTextChange
import com.contacts.mazaady.databinding.LayoutListSelectorBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.ArrayList

class SelectProperitiesOptionBottomSheet(
    private val propsOption:List<Option>,
    private val itemSelected:(Option)->Unit,
) : BottomSheetDialogFragment() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        LayoutListSelectorBottomSheetBinding.inflate(
            layoutInflater
        )
    }

    private val adapterProperitiesOption by lazy {
        AdapterProperitiesOption(itemSelected = {
            itemSelected(it)
            dismiss()
        })
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

        addListenersOnViews()
        setUpCategories()

    }

    private fun setUpCategories() {
        binding.rvSelector.adapter = adapterProperitiesOption
        adapterProperitiesOption.submitList(propsOption)
    }

    private fun addListenersOnViews() {
        binding.edSearch.onTextChange {
            val newList = ArrayList<Option>()
            for (i in propsOption) {
                if (i.slug?.contains(it) == true) {
                    newList.add(i)
                }
            }
            adapterProperitiesOption.submitList(newList)
        }
    }


    private fun openFullScreen() {
        val dialog = dialog as BottomSheetDialog
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onStart() {
        super.onStart()
        openFullScreen()
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }
}