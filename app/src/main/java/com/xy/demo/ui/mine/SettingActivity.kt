package com.xy.demo.ui.mine

import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivitySettingBinding

class SettingActivity : MBBaseActivity<ActivitySettingBinding,MBBaseViewModel>() {

    override fun getLayoutId(): Int {
       return R.layout.activity_setting
    }
}