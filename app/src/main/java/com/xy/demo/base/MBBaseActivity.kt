package com.xy.demo.base

import com.xy.demo.BR
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.xy.demo.R
import com.xy.xframework.base.BaseSharePreference
import com.xy.xframework.base.XBaseActivity
import com.xy.xframework.base.XBaseViewModel
import com.xy.xframework.statusBar.StatusBarUtil

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
}
