package com.xy.demo.ui.main.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.model.CheckSettingModel
import com.xy.demo.model.VideoStoreModel
import com.xy.demo.network.NetManager
import com.xy.demo.network.params.ReaderParams
import com.xy.xframework.command.SingleLiveEvent
import com.xy.xframework.utils.ToastUtils


class MineViewModel(application: Application) : MBBaseViewModel(application) {

    val checkSettingModel = SingleLiveEvent<CheckSettingModel>()
    val videoStoreModel = MutableLiveData<VideoStoreModel>()

    fun getUserInfo(readerParams: ReaderParams) {
        launchRequest({
            NetManager.getUserInfo(readerParams.generateParamsMap())
        }, {

        }, {
            ToastUtils.showShort(it.message)
        })

    }




}