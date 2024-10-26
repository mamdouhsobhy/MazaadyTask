package com.example.rehla.core.data.utils

import com.google.gson.annotations.SerializedName

data class WrappedListResponse<T>(
    var code: Int,
    @SerializedName("message") var message: String,
    @SerializedName("status") var status: Boolean,
    @SerializedName("errors") var errors: List<String>? = null,
    @SerializedName("data") var data: List<T>? = null
)

data class WrappedResponse<T>(
    @SerializedName("data") val data: T? = null,
    @SerializedName("code") val code: Int? =null,
    @SerializedName("msg") val msg: String? = null
)
