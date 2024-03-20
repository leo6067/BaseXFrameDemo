package com.xy.demo.ui.launch


import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityLoginBinding
import com.xy.demo.vm.LoginViewModel

class LoginActivity : MBBaseActivity<ActivityLoginBinding, LoginViewModel>() {


    override fun getLayoutId(): Int = R.layout.activity_login
}