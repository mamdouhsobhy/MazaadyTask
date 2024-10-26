package com.contacts.mazaady.core.data.utils

import com.example.rehla.core.data.utils.WrappedResponse
import com.contacts.mazaady.core.presentation.base.BaseResult
import com.contacts.mazaady.core.presentation.base.BaseState
import kotlinx.coroutines.flow.*
import java.net.UnknownHostException
import javax.inject.Inject

class ExecuteResponse @Inject constructor() {

    fun <T : Any> execute(
        flow: Flow<BaseResult<WrappedResponse<T>>>,
        state: MutableStateFlow<BaseState<T>>
    ): Flow<BaseState<T>> {
        return flow
            .onStart { state.value = BaseState.IsLoading(true) }
            .catch { e ->
                state.value = BaseState.IsLoading(false)
                state.value = BaseState.ShowToast(
                    e.message.toString(),
                    e is UnknownHostException
                )
            }
            .map {
                state.value = BaseState.IsLoading(false)
                when (it) {
                    is BaseResult.ErrorState -> BaseState.Error(it.code, it.message)
                    is BaseResult.DataState -> BaseState.Success(it.items?.data)
                }
            }
    }

}