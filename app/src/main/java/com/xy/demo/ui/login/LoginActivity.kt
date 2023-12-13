package com.xy.demo.ui.login


import android.content.Intent
import android.net.Uri
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityLoginBinding
import com.xy.demo.ui.login.viewmodel.LoginViewModel
import com.xy.demo.ui.main.MainActivity

class LoginActivity : MBBaseActivity<ActivityLoginBinding, LoginViewModel>() {


    override fun getLayoutId(): Int = R.layout.activity_login

    override fun showTitleBar(): Boolean {
        return false
    }


    override fun initView() {
        super.initView()


        binding.ykButton.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }


        binding.linkButton.setOnClickListener {




        }
    }


}