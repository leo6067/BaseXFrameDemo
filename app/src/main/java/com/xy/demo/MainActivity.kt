package com.xy.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityMainBinding
import com.xy.demo.network.NetLaunchManager
import com.xy.demo.network.NetLaunchManager.launchRequest
import com.xy.demo.network.NetManager

import com.xy.xframework.base.XBaseActivity
import com.xy.xframework.base.XBaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : MBBaseActivity<ActivityMainBinding, XBaseViewModel>() {
    override fun getLayoutId(): Int = R.layout.activity_main


    override fun showTitleBar(): Boolean = false

    override fun fitsSystemWindows(): Boolean {
        return false
    }

    override fun initView() {

        launchRequest({NetManager.getStoreCount()},{},{})

        GlobalScope.launch {
            delay(300)

        }
    }

}