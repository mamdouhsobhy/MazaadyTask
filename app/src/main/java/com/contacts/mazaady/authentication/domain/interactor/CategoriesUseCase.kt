package com.contacts.mazaady.authentication.domain.interactor

import com.contacts.mazaady.authentication.data.responseremote.getAllCategories.ModelGetAllCategoriesResponseRemote
import com.contacts.mazaady.authentication.data.responseremote.getProperitiesChild.ModelGetProperitiesChildResponseRemote
import com.contacts.mazaady.authentication.data.responseremote.properities.ModelGetPropritiesResponseRemote
import com.contacts.mazaady.authentication.domain.repository.AuthorizationRepository
import com.contacts.mazaady.core.presentation.base.BaseResult
import com.example.rehla.core.data.utils.WrappedResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoriesUseCase @Inject constructor(private val repository: AuthorizationRepository) {

    suspend fun executeGetAllCategories(): Flow<BaseResult<WrappedResponse<ModelGetAllCategoriesResponseRemote>>> {
        return repository.getAllCategories()
    }

    suspend fun executeGetProperties(catId:String): Flow<BaseResult<WrappedResponse<List<ModelGetPropritiesResponseRemote>>>> {
        return repository.properties(catId)
    }

    suspend fun executeGetPropertiesChild(optionId:String): Flow<BaseResult<WrappedResponse<List<ModelGetProperitiesChildResponseRemote>>>> {
        return repository.propertiesChild(optionId)
    }

}