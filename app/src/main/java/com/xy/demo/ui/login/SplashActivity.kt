package com.xy.demo.ui.login

import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivitySplashBinding
import com.xy.demo.network.NetLaunchManager

import com.xy.demo.network.NetManager
import com.xy.demo.network.params.ReaderParams
import com.xy.xframework.utils.ToastUtils
import kotlinx.coroutines.delay

class SplashActivity : MBBaseActivity<ActivitySplashBinding, MBBaseViewModel>() {


    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun fitsSystemWindows(): Boolean {
        return false
    }

    override fun showTitleBar(): Boolean {
        return false
    }

    override fun initParams() {
        super.initParams()
        val readerParams = ReaderParams(this)


        NetLaunchManager.launchRequest({
            NetManager.getCheckSetting(readerParams.generateParamsMap())

        }, {

        }) {
            ToastUtils.showShort(it.message)
        }
    }
}