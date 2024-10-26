package com.contacts.mazaady.authentication.domain.repository

import com.contacts.mazaady.authentication.data.responseremote.getAllCategories.ModelGetAllCategoriesResponseRemote
import com.contacts.mazaady.authentication.data.responseremote.getProperitiesChild.ModelGetProperitiesChildResponseRemote
import com.contacts.mazaady.authentication.data.responseremote.properities.ModelGetPropritiesResponseRemote
import com.contacts.mazaady.core.presentation.base.BaseResult
import com.example.rehla.core.data.utils.WrappedResponse
import kotlinx.coroutines.flow.Flow

interface AuthorizationRepository {

    suspend fun getAllCategories(): Flow<BaseResult<WrappedResponse<ModelGetAllCategoriesResponseRemote>>>

    suspend fun properties(catId:String): Flow<BaseResult<WrappedResponse<List<ModelGetPropritiesResponseRemote>>>>

    suspend fun propertiesChild(optionId:String): Flow<BaseResult<WrappedResponse<List<ModelGetProperitiesChildResponseRemote>>>>

}