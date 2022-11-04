package com.xy.demo.ui


import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityLoginBinding

class LoginActivity : MBBaseActivity<ActivityLoginBinding,LoginViewModel>() {


    override fun getLayoutId(): Int = R.layout.activity_login
}