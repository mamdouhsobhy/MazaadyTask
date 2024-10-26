package com.contacts.mazaady.authentication.presentation.view.category

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.contacts.mazaady.home.MainActivity
import com.contacts.mazaady.R
import com.contacts.mazaady.authentication.data.responseremote.getAllCategories.Category
import com.contacts.mazaady.authentication.data.responseremote.getAllCategories.Children
import com.contacts.mazaady.authentication.data.responseremote.getAllCategories.ModelGetAllCategoriesResponseRemote
import com.contacts.mazaady.authentication.data.responseremote.getProperitiesChild.ModelGetProperitiesChildResponseRemote
import com.contacts.mazaady.authentication.data.responseremote.properities.ModelGetPropritiesResponseRemote
import com.contacts.mazaady.authentication.data.responseremote.properities.Option
import com.contacts.mazaady.authentication.presentation.view.category.adapter.AdapterProperities
import com.contacts.mazaady.authentication.presentation.view.category.bottomsheet.SelectCategoryBottomSheet
import com.contacts.mazaady.authentication.presentation.view.category.bottomsheet.SelectProperitiesOptionBottomSheet
import com.contacts.mazaady.authentication.presentation.view.category.bottomsheet.SelectSubCategoryBottomSheet
import com.contacts.mazaady.authentication.presentation.view.category.bottomsheet.ShowSelectPropsBottomSheet
import com.contacts.mazaady.authentication.presentation.view.category.model.ModelSelectedProps
import com.contacts.mazaady.authentication.presentation.view.category.viewmodel.CategoryViewModel
import com.contacts.mazaady.core.presentation.base.BaseFragmentBinding
import com.contacts.mazaady.core.presentation.base.BaseState
import com.contacts.mazaady.core.presentation.extensions.TxT
import com.contacts.mazaady.core.presentation.extensions.showGenericAlertDialog
import com.contacts.mazaady.core.presentation.extensions.showInfoMessage
import com.contacts.mazaady.core.presentation.extensions.showToast
import com.contacts.mazaady.databinding.FragmentCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FragmentCategory : BaseFragmentBinding<FragmentCategoryBinding>() {

    private val viewModel: CategoryViewModel by viewModels()

    private val adapterProperities by lazy {
        AdapterProperities(itemSelected = { item, index ->
            currentProperityIndex = index
            openProperitiesOptionsBottomSheet(item, index)
        })
    }

    lateinit var subCategories: ArrayList<Children>
    var subCategoryId = ""
    var currentProperityIndex = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addListenerOnView()
        observeStateFlow()
        setUpInputs()

    }

    private fun setUpInputs() {
        binding.rvInputs.adapter = adapterProperities
    }

    private fun addListenerOnView() {

        binding.edMainCategory.setOnClickListener {
            viewModel.isCategoriesLoading = false
            viewModel.getAllCategories()
        }

        binding.edSubCategory.setOnClickListener {
            if (binding.edMainCategory.TxT().isNotEmpty()) {
                openSubCategoriesBottomSheet()
            } else {
                requireContext().showInfoMessage(message = getString(R.string.must_select_main_category_first))
            }
        }

        binding.btnSubmit.setOnClickListener {
            val selectedProps = ArrayList<ModelSelectedProps>()
            if(adapterProperities.currentList.isNotEmpty()) {
                for (items in adapterProperities.currentList) {
                    if (items.isOtherValue) {
                        val otherValue = items.other_value?:""
                        if (otherValue.isEmpty()) {
                            returnToCompleteData()
                            return@setOnClickListener
                        }
                        selectedProps.add(ModelSelectedProps(items.id, items.slug, items.other_value))
                    } else {
                        if (items.value.isEmpty()) {
                            returnToCompleteData()
                            return@setOnClickListener
                        }
                        selectedProps.add(ModelSelectedProps(items.id, items.slug, items.value))
                    }
                }
                openSelectedProps(selectedProps)
            }else{
                returnToCompleteData()
            }

        }
    }

    private fun returnToCompleteData(){
        requireContext().showInfoMessage("Must Complete All Inputs Data")
    }

    private fun openSelectedProps(selectedProps: ArrayList<ModelSelectedProps>) {
        ShowSelectPropsBottomSheet(selectedProps) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }.show(parentFragmentManager, "")

    }

    private fun openProperitiesOptionsBottomSheet(
        item: ModelGetPropritiesResponseRemote,
        index: Int,
    ) {

        val options = ArrayList<Option>()
        options.add(Option(false, item.slug, "Other", 0, "Other"))
        options.addAll(item.options as ArrayList<Option>)

        SelectProperitiesOptionBottomSheet(options) {
            updateAdapterWithNewProperities(it.slug, index)
            if (it.child == true && it.slug != "Other") {
                viewModel.isPropertiesChildLoading = false
                viewModel.propertiesChild("${it.id}")
            }
        }.show(parentFragmentManager, "options")
    }

    private fun updateAdapterWithNewProperities(slug: String?, index: Int) {
        if (slug == "Other") {
            adapterProperities.currentList[index].isOtherValue = true
        } else {
            adapterProperities.currentList[index].isOtherValue = false
            adapterProperities.currentList[index].other_value = ""
        }
        adapterProperities.currentList[index].value = slug!!
        adapterProperities.notifyDataSetChanged()
    }

    private fun openCategoriesBottomSheet(category: ArrayList<Category>) {
        SelectCategoryBottomSheet(category) {
            subCategories = it.children as ArrayList<Children>
            binding.edMainCategory.setText(it.slug)
            binding.edSubCategory.setText("")
            adapterProperities.submitList(emptyList())
            subCategoryId = ""
        }.show(parentFragmentManager, "categories")
    }

    private fun openSubCategoriesBottomSheet() {
        SelectSubCategoryBottomSheet(subCategories) {
            binding.edSubCategory.setText(it.slug)
            subCategoryId = "${it.id}"
            callProprities(subCategoryId)
        }.show(parentFragmentManager, "sub Categories")
    }

    private fun callProprities(catId: String) {
        viewModel.isPropertiesLoading = false
        viewModel.properties(catId)
    }

    private fun observeStateFlow() {
        viewModel.categoryState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.propertiesState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handlePropertiesStateChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.propertiesChildState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handlePropertiesChildStateChange(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    //region handle categoryState
    private fun handleStateChange(state: BaseState<ModelGetAllCategoriesResponseRemote>) {
        when (state) {
            is BaseState.Init -> Unit
            is BaseState.Error -> handleError(
                state.code,
                state.message
            )

            is BaseState.Success -> handleSuccess(state.items)
            is BaseState.ShowToast -> {
                if (viewModel.isCategoriesLoading.not()) {
                    requireActivity().showToast(
                        state.message,
                        state.isConnectionError
                    )
                    viewModel.isCategoriesLoading = true
                }
            }

            is BaseState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun handleSuccess(cats: ModelGetAllCategoriesResponseRemote?) {

        if (viewModel.isCategoriesLoading.not()) {
            openCategoriesBottomSheet(cats?.categories as ArrayList<Category>)
            viewModel.isCategoriesLoading = true
        }
    }
    //endregion

    //region handle propertiesState
    private fun handlePropertiesStateChange(state: BaseState<List<ModelGetPropritiesResponseRemote>>) {
        when (state) {
            is BaseState.Init -> Unit
            is BaseState.Error -> handleError(
                state.code,
                state.message
            )

            is BaseState.Success -> handleSuccessProperties(state.items)
            is BaseState.ShowToast -> {
                if (viewModel.isPropertiesLoading.not()) {
                    requireActivity().showToast(
                        state.message,
                        state.isConnectionError
                    )
                    viewModel.isPropertiesLoading = true
                }
            }

            is BaseState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun handleSuccessProperties(props: List<ModelGetPropritiesResponseRemote>?) {
        if (viewModel.isPropertiesLoading.not()) {
            adapterProperities.submitList(props)
            viewModel.isPropertiesLoading = true
        }
    }
    //endregion

    //region handle propertiesChildState
    private fun handlePropertiesChildStateChange(state: BaseState<List<ModelGetProperitiesChildResponseRemote>>) {
        when (state) {
            is BaseState.Init -> Unit
            is BaseState.Error -> handleError(
                state.code,
                state.message
            )

            is BaseState.Success -> handleSuccessPropertiesChild(state.items)
            is BaseState.ShowToast -> {
                if (viewModel.isPropertiesChildLoading.not()) {
                    requireActivity().showToast(
                        state.message,
                        state.isConnectionError
                    )
                    viewModel.isPropertiesChildLoading = true
                }
            }

            is BaseState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun handleSuccessPropertiesChild(child: List<ModelGetProperitiesChildResponseRemote>?) {
        if (viewModel.isPropertiesChildLoading.not()) {
            val propertiesList = child?.map { childItem ->
                ModelGetPropritiesResponseRemote(
                    description = childItem.description?.toString(),
                    id = childItem.id?.toString(),
                    list = childItem.list,
                    name = childItem.name,
                    options = childItem.options?.map { option ->
                        Option(
                            child = option?.child,
                            id = option?.id?.toString(),
                            name = option?.name,
                            parent = option?.parent,
                            slug = option?.slug
                        )
                    },
                    other_value = childItem.other_value ?: "",
                    parent = childItem.parent,
                    slug = childItem.slug,
                    type = childItem.type?.toString(),
                    value = childItem.value ?: "",
                    isOtherValue = false
                )
            }

            val properities = adapterProperities.currentList.toMutableList()
            properities.add(currentProperityIndex + 1, propertiesList?.get(0))

            adapterProperities.submitList(properities)
            adapterProperities.notifyDataSetChanged()
            viewModel.isPropertiesChildLoading = true
        }
    }
    //endregion

    //region handle loading and error
    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) showLoading()
        else dismissLoading()
    }

    private fun handleError(code: String, message: String) {
        if (
            viewModel.isCategoriesLoading.not() ||
            viewModel.isPropertiesLoading.not() ||
            viewModel.isPropertiesChildLoading.not()
        ) {
            requireActivity().showGenericAlertDialog(
                parentFragmentManager,
                code = code.toInt(),
                message = message
            )
            viewModel.isCategoriesLoading = true
            viewModel.isPropertiesLoading = true
            viewModel.isPropertiesChildLoading = true
        }
    }
    //endregion


}