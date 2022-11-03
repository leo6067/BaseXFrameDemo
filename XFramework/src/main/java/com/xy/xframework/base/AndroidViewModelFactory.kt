package com.xy.xframework.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AndroidViewModelFactory(
    val application: Application,
    var create: (() -> ViewModel?)? = null
) : ViewModelProvider.AndroidViewModelFactory(application) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        create?.invoke() as? T ?: super.create(modelClass)
}