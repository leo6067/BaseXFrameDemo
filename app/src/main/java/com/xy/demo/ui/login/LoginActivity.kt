package com.xy.demo.ui.login


import android.content.Intent
import android.view.View
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityLoginBinding
import com.xy.demo.ui.main.MainActivity
import com.xy.demo.ui.vm.LoginViewModel

class LoginActivity : MBBaseActivity<ActivityLoginBinding, LoginViewModel>() {
    
    
    override fun showTitleBar(): Boolean {
        return super.showTitleBar()
    }
    
    
    override fun getLayoutId(): Int = R.layout.activity_login
    
    
    override fun initView() {
        super.initView()
        titleBarView?.tvTitle?.text = "登录"
        titleBarView?.hideBackBtn()
        
        
    }
    
    
    override fun onClick(view:View){
        when(view){
            binding.loginTV->{
                startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                finish()
            }
            binding.regisTV->{}
            
            
            
        }
        
        
    }


}