package com.contacts.mazaady.core.presentation.base


interface PermissionCallBack {
    fun onPermissionGranted()

    fun onResultContainsDenied()
}