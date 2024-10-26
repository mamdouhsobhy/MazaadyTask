package com.contacts.mazaady.core.data.utils

import com.contacts.mazaady.core.presentation.common.SharedPrefs
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor constructor(private val pref: SharedPrefs) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest = chain.request().newBuilder()
            .addHeader(Constant.privateKey, Constant.keyValue)
            .build()
        return chain.proceed(newRequest)
    }
}