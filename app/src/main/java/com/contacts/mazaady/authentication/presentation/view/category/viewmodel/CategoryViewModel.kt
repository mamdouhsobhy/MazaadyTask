package com.contacts.mazaady.authentication.presentation.view.category.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contacts.mazaady.authentication.data.responseremote.getAllCategories.ModelGetAllCategoriesResponseRemote
import com.contacts.mazaady.authentication.data.responseremote.getProperitiesChild.ModelGetProperitiesChildResponseRemote
import com.contacts.mazaady.authentication.data.responseremote.properities.ModelGetPropritiesResponseRemote
import com.contacts.mazaady.authentication.domain.interactor.CategoriesUseCase
import com.contacts.mazaady.core.data.utils.ExecuteResponse
import com.contacts.mazaady.core.presentation.base.BaseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoriesUseCase: CategoriesUseCase,
    private val executeResponse: ExecuteResponse
) : ViewModel() {

    private val _categoryState = MutableStateFlow<BaseState<ModelGetAllCategoriesResponseRemote>>(BaseState.Init)
    val categoryState: StateFlow<BaseState<ModelGetAllCategoriesResponseRemote>> get() = _categoryState

    private val _propertiesState = MutableStateFlow<BaseState<List<ModelGetPropritiesResponseRemote>>>(BaseState.Init)
    val propertiesState: StateFlow<BaseState<List<ModelGetPropritiesResponseRemote>>> get() = _propertiesState

    private val _propertiesChildState = MutableStateFlow<BaseState<List<ModelGetProperitiesChildResponseRemote>>>(BaseState.Init)
    val propertiesChildState: StateFlow<BaseState<List<ModelGetProperitiesChildResponseRemote>>> get() = _propertiesChildState

    var isCategoriesLoading = false
    var isPropertiesLoading = false
    var isPropertiesChildLoading = false


    fun getAllCategories() {
        viewModelScope.launch {
            executeResponse.execute(categoriesUseCase.executeGetAllCategories(), _categoryState)
                .collect {
                    _categoryState.value = it
                }
        }
    }

    fun properties(catId:String) {
        viewModelScope.launch {
            executeResponse.execute(categoriesUseCase.executeGetProperties(catId), _propertiesState)
                .collect {
                    _propertiesState.value = it
                }
        }
    }

    fun propertiesChild(optionId:String) {
        viewModelScope.launch {
            executeResponse.execute(categoriesUseCase.executeGetPropertiesChild(optionId), _propertiesChildState)
                .collect {
                    _propertiesChildState.value = it
                }
        }
    }

}
