package com.xy.demo.ui.main.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.model.CheckSettingModel
import com.xy.demo.model.VideoStoreModel
import com.xy.demo.network.NetManager
import com.xy.demo.network.params.ReaderParams
import com.xy.xframework.utils.ToastUtils


class MainViewModel(application: Application) : MBBaseViewModel(application) {

    val checkSettingModel = MutableLiveData<CheckSettingModel>()
    val videoStoreModel = MutableLiveData<VideoStoreModel>()

    fun getCheckSetting(readerParams: ReaderParams) {
        launchRequest({
            NetManager.getCheckSetting(readerParams.generateParamsMap())
        }, {
            checkSettingModel.value = it
        }, {
            ToastUtils.showShort(it.message)
        })
    }


    fun getVideoStore(readerParams: ReaderParams) {
        launchRequest({
            NetManager.getVideoStore(readerParams.generateParamsMap())
        }, {
            videoStoreModel.value = it
        }, {
            ToastUtils.showShort(it.message)
        })
    }


}