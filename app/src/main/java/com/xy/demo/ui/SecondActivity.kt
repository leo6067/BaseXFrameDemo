package com.xy.demo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivitySecondBinding

class SecondActivity : MBBaseActivity<ActivitySecondBinding,LoginViewModel>() {


    override fun getLayoutId(): Int = R.layout.activity_second

    override fun initView() {
        super.initView()
        binding.backTV.setOnClickListener {
            finish()
        }
    }
}