package com.contacts.mazaady.authentication.data.repositoryimb

import com.contacts.mazaady.authentication.data.datasource.AuthorizationService
import com.contacts.mazaady.authentication.data.responseremote.getAllCategories.ModelGetAllCategoriesResponseRemote
import com.contacts.mazaady.authentication.data.responseremote.getProperitiesChild.ModelGetProperitiesChildResponseRemote
import com.contacts.mazaady.authentication.data.responseremote.properities.ModelGetPropritiesResponseRemote
import com.contacts.mazaady.authentication.domain.repository.AuthorizationRepository
import com.contacts.mazaady.core.data.utils.EmittingResponse
import com.contacts.mazaady.core.presentation.base.BaseResult
import com.example.rehla.core.data.utils.WrappedResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthorizationRepositoryImpl @Inject constructor(
    private val service: AuthorizationService,
    private val emittingResponse: EmittingResponse
) : AuthorizationRepository {

    override suspend fun getAllCategories(): Flow<BaseResult<WrappedResponse<ModelGetAllCategoriesResponseRemote>>> {
        return emittingResponse.makeApiCall { service.getAllCategories() }
    }

    override suspend fun properties(catId: String): Flow<BaseResult<WrappedResponse<List<ModelGetPropritiesResponseRemote>>>> {
        return emittingResponse.makeApiCall { service.properties(catId) }
    }

    override suspend fun propertiesChild(optionId: String): Flow<BaseResult<WrappedResponse<List<ModelGetProperitiesChildResponseRemote>>>> {
        return emittingResponse.makeApiCall { service.propertiesChild(optionId) }
    }

}