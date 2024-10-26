package com.contacts.mazaady

import android.app.Application
import com.contacts.mazaady.core.presentation.common.SharedPrefs
import com.contacts.mazaady.core.presentation.utilities.LocaleHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MazaadyApplication : Application() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    var wasInBackground = false

    override fun onCreate() {
        super.onCreate()
        LocaleHelper.onAttach(applicationContext)
        LocaleHelper.setLocale(sharedPrefs.getPreferredLocale())
    }

}