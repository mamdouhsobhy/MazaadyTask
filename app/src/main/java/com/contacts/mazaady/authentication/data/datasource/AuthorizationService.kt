package com.contacts.mazaady.authentication.data.datasource

import com.contacts.mazaady.authentication.data.responseremote.getAllCategories.ModelGetAllCategoriesResponseRemote
import com.contacts.mazaady.authentication.data.responseremote.getProperitiesChild.ModelGetProperitiesChildResponseRemote
import com.contacts.mazaady.authentication.data.responseremote.properities.ModelGetPropritiesResponseRemote
import com.example.rehla.core.data.utils.WrappedResponse
import retrofit2.Response
import retrofit2.http.*

interface AuthorizationService {

    @GET("get_all_cats")
    suspend fun getAllCategories(): Response<WrappedResponse<ModelGetAllCategoriesResponseRemote>>

    @GET("properties")
    suspend fun properties(
        @Query("cat") cat: String): Response<WrappedResponse<List<ModelGetPropritiesResponseRemote>>>

    @GET("get-options-child/{id}")
    suspend fun propertiesChild(
        @Path("id") optionId: String): Response<WrappedResponse<List<ModelGetProperitiesChildResponseRemote>>>


}