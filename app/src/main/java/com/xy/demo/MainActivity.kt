package com.xy.demo

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
import com.xy.demo.network.Globals
import com.xy.demo.network.NetLaunchManager.launchRequest
import com.xy.demo.network.NetManager
import com.xy.demo.ui.SecondActivity
import com.xy.xframework.base.BaseAppContext
import com.xy.xframework.base.BaseSharePreference
import com.xy.xframework.base.XBaseViewModel

import java.io.File


class MainActivity : MBBaseActivity<ActivityMainBinding, XBaseViewModel>() {


    var downloadId = 0
    override fun getLayoutId(): Int = R.layout.activity_main


    override fun showTitleBar(): Boolean = false

    override fun fitsSystemWindows(): Boolean {
        return false
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        if (BaseSharePreference.instance.getString("AppTheme","light").equals("night")) {
            //设置夜晚主题  需要在setContentView之前
            setTheme(R.style.AppDarkTheme)
        } else {
            //设置白天主题
            setTheme(R.style.AppLightTheme);
        }
        super.onCreate(savedInstanceState)
    }



    override fun initView() {

        launchRequest({ NetManager.getStoreCount() }, {}, {})


        var downUrl =
            "https://softforspeed.51xiazai.cn/alading/NeteaseCloudMusic_Music_official_2.10.6.200601.exe"


        val rootDirPath = getRootDirPath(BaseAppContext.getInstance());
        Globals.log("xxxxxfilesDir", getRootDirPath(BaseAppContext.getInstance()))
        Globals.log("xxxxxfilesDir--本地可用的存储 路劲", filesDir.absolutePath.toString() + "新增的文件名")



        binding.nightTV.setOnClickListener {


            BaseSharePreference.instance.putString("AppTheme", "night")
            recreate()
            // 暗黑模式
//            setTheme(R.style.AppDarkTheme)

//            startActivity(Intent(this@MainActivity, MainActivity::class.java))


        }

        binding.lightTV.setOnClickListener {
            BaseSharePreference.instance.putString("AppTheme", "light")
            recreate()
//            setTheme(R.style.AppLightTheme)
//            startActivity(Intent(this@MainActivity, MainActivity::class.java))
        }




        binding.nextTV.setOnClickListener {
//            startActivity(Intent(this@MainActivity, SecondActivity::class.java))
            var url = "https://HiReadNovel.onelink.me/9gP0/vplay?v=66&c=0";
            var uri = Uri.parse(url);
            var intent = Intent();
            intent.setData(uri);
            startActivity(intent);
        }
    }



    override fun onResume() {
        super.onResume()

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