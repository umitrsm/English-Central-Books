package com.egb.englishcentralbooks

import android.app.Application
import com.egb.englishcentralbooks.extensions.logInfo
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EnglishCenterApp : Application() {

    override fun onCreate() {
        super.onCreate()
        logInfo("English Central Book App started")
    }
}
