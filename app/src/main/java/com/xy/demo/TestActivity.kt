package com.xy.demo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.core.content.ContextCompat
import com.xy.demo.network.Globals
import com.xy.demo.network.NetLaunchManager
import com.xy.demo.network.NetManager
import com.xy.xframework.base.BaseAppContext
import com.xy.xframework.base.BaseSharePreference
import java.io.File

class TestActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		if (BaseSharePreference.spObject.getString("AppTheme", "light").equals("night")) {
			//设置夜晚主题  需要在setContentView之前
			setTheme(R.style.AppDarkTheme)
		} else {
			//设置白天主题
			setTheme(R.style.AppLightTheme);
		}
		
		setContentView(R.layout.activity_test)
		
		
		
		
		NetLaunchManager.launchRequest({ NetManager.getStoreCount() }, {}, {})
		
		
		var downUrl =
			"https://softforspeed.51xiazai.cn/alading/NeteaseCloudMusic_Music_official_2.10.6.200601.exe"
		
		
		val rootDirPath = getRootDirPath(BaseAppContext.getInstance());
		Globals.log("xxxxxfilesDir", getRootDirPath(BaseAppContext.getInstance()))
		Globals.log("xxxxxfilesDir--本地可用的存储 路劲", filesDir.absolutePath.toString() + "新增的文件名")
		
		
//		binding.nightTV.setOnClickListener {
//			BaseSharePreference.spObject.putString("AppTheme", "night")
//			recreate()
//			// 暗黑模式
////            setTheme(R.style.AppDarkTheme)
////            startActivity(Intent(this@MainActivity, MainActivity::class.java))
//		}
//
//		binding.lightTV.setOnClickListener {
//			BaseSharePreference.spObject.putString("AppTheme", "light")
//			recreate()
//		}
	
	}
	
	
	fun getRootDirPath(context: Context): String? {
		return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
			val file: File = ContextCompat.getExternalFilesDirs(
				context.applicationContext,
				null
			).get(0)
			file.absolutePath
		} else {
			context.applicationContext.filesDir.absolutePath
		}
	}
}