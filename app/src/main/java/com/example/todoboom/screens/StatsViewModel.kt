package com.example.todoboom.screens

import androidx.lifecycle.ViewModel
import timber.log.Timber

class StatsViewModel : ViewModel() {
    init {
        Timber.i("StatsViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("StatsViewModel destroyed")
    }
}