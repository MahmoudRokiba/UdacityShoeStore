package com.rokiba.udacityshoestore

import android.content.Context
import com.zeugmasolutions.localehelper.LocaleAwareApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : LocaleAwareApplication() {

    init {
        instance = this
    }

    companion object {
        private var instance: MyApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

}