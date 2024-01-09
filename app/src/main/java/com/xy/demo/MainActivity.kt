package com.xy.demo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityMainBinding
import com.xy.demo.logic.ConsumerIrManagerApi
import com.xy.demo.logic.NecPattern
import com.xy.demo.network.Globals
import com.xy.demo.network.NetLaunchManager.launchRequest
import com.xy.demo.network.NetManager
import com.xy.demo.ui.SecondActivity
import com.xy.xframework.base.BaseAppContext
import com.xy.xframework.base.BaseSharePreference
import com.xy.xframework.base.XBaseViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : MBBaseActivity<ActivityMainBinding, XBaseViewModel>() {
	
 
	override fun getLayoutId(): Int = R.layout.activity_main
	
	
	override fun showTitleBar(): Boolean = true
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	
 
	
	@SuppressLint("ResourceAsColor")
	override fun initView() {
	
		
		titleBarView?.tvTitle?.text = "红外遥控器"
		
		titleBarView?.tvTitle?.setTextColor(R.color.color_333333)
		
		
 
		
		/**
		 * 按下按键发射红外信号
		 */
		binding.nextTV.setOnClickListener {

//            startActivity(Intent(this@MainActivity, SecondActivity::class.java))


//            var url = "https://HiReadNovel.onelink.me/9gP0/vplay?v=66&c=0";
//            var uri = Uri.parse(url);
//            var intent = Intent();
//            intent.setData(uri);
//            startActivity(intent);
			
			
			
			ConsumerIrManagerApi.getConsumerIrManager(this).transmit(38000, NecPattern.buildPattern(0X08, 0XE6, 0X41));
			
			
		}
		
		
	}
 
	

	
}