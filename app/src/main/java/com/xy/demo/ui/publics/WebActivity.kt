package com.xy.demo.ui.publics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityWebBinding
import com.xy.xframework.web.WebBaseActivity
import com.xy.xframework.web.WebViewJS
import com.xy.xframework.web.WebViewUIChangeEvent

class WebActivity : WebBaseActivity() {
    override fun getWebViewJs(): WebViewJS {
        return  WebViewJS(WebViewUIChangeEvent());
    }


    override fun initView() {

    }
}