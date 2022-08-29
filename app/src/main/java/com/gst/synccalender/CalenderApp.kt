package com.gst.synccalender

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


/**
 * Created by gideon on 8/26/2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@HiltAndroidApp
class CalenderApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}