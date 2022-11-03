package com.xy.network.watch

import androidx.lifecycle.LiveData


object NetworkStateLiveData : LiveData<NetworkType>() {

    fun setNetState(type: NetworkType) {
        postValue(type)
    }

    override fun onActive() {
        super.onActive()
    }

    override fun onInactive() {
        super.onInactive()
    }
}