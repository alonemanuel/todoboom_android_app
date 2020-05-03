package com.example.todoboom

import android.app.Application
import timber.log.Timber

class TodoboomApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

    }

}