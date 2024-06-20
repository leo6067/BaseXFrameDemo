package com.xy.demo.base

import com.xy.demo.BR
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.NetworkUtils
import com.xy.demo.R
import com.xy.network.watch.NetworkStateLiveData
import com.xy.network.watch.NetworkType
import com.xy.xframework.base.BaseSharePreference
import com.xy.xframework.base.XBaseActivity
import com.xy.xframework.base.XBaseViewModel
import com.xy.xframework.statusBar.StatusBarUtil
import com.xy.xframework.utils.ToastUtils

abstract class MBBaseActivity<T : ViewDataBinding, VM : XBaseViewModel> : XBaseActivity<T, VM>() {

    val TAG: String = this::class.java.simpleName

    override fun initVariableId(): Int = BR.viewModel

    override fun isSwipeBackClose(): Boolean = true

    override fun initView() {
        StatusBarUtil.setStatusBarDarkTheme(this, true)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        ARouter.getInstance().inject(this)

        if (BaseSharePreference.instance.getString("AppTheme","light").equals("night")) {
            //设置夜晚主题  需要在setContentView之前
            setTheme(R.style.AppDarkTheme)
        } else {
            //设置白天主题
            setTheme(R.style.AppLightTheme);
        }


        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
//        logger("@AF", "onResume() -> ${this::class.java.simpleName}")
    }
    
    
    override fun initParams() {
        super.initParams()
        var hasNetWork = NetworkUtils.isConnected()
        NetworkStateLiveData.observe(this) {
            if (it == NetworkType.CONNECT) {
                ToastUtils.showShort("网络已连接")
                hasNetWork = true
            }
            if (it == NetworkType.NONE) {
                ToastUtils.showShort("网络已断开")
                hasNetWork = false
            }
        }
        
        if (hasNetWork) {
            if (notNetWorkLin != null) {
                notNetWorkLin?.visibility = View.GONE
            }
        } else {
            if (notNetWorkLin != null) {
                notNetWorkLin?.visibility = View.VISIBLE
            }
        }
        
    }
}
