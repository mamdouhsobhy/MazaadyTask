package com.contacts.mazaady.core.data.utils

import com.example.rehla.core.data.utils.WrappedResponse
import com.contacts.mazaady.core.presentation.base.BaseResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class EmittingResponse @Inject constructor() {

    suspend fun <T : Any> makeApiCall(call: suspend () -> Response<WrappedResponse<T>>): Flow<BaseResult<WrappedResponse<T>>> {
        return flow {
            val response = call.invoke()
            if (response.isSuccessful) {
                val body = response.body()!!
                emit(BaseResult.DataState(body))
            } else {
                if (response.code() == 301) {
                    emit(
                        BaseResult.ErrorState(
                            "401",
                            "un authenticated"
                        )
                    )
                } else {
                    val type = object : TypeToken<WrappedResponse<T>>() {}.type
                    val err: WrappedResponse<T> =
                        Gson().fromJson(response.errorBody()!!.charStream(), type)
                    emit(
                        BaseResult.ErrorState(
                            "${response.code()}",
                            "${err.msg}"
                        )
                    )
                }
            }
        }
    }

}