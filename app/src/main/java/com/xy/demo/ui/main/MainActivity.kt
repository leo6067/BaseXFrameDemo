package com.xy.demo.ui.main

import android.content.Context
import android.os.Bundle
import android.os.Environment
import androidx.core.content.ContextCompat
import com.xy.demo.R

import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityMainBinding
import com.xy.demo.network.Globals

import com.xy.xframework.base.BaseAppContext
import com.xy.xframework.base.BaseSharePreference
import com.xy.xframework.base.XBaseViewModel
import java.io.File


class MainActivity : MBBaseActivity<ActivityMainBinding, XBaseViewModel>() {


    override fun getLayoutId(): Int = R.layout.activity_main


    override fun showTitleBar(): Boolean = false

    override fun fitsSystemWindows(): Boolean {
        return false
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        if (BaseSharePreference.spObject.getString("AppTheme", "light").equals("night")) {
            //设置夜晚主题  需要在setContentView之前
            setTheme(R.style.AppDarkTheme)
        } else {
            //设置白天主题
            setTheme(R.style.AppLightTheme);
        }
        super.onCreate(savedInstanceState)
    }


    override fun initView() {




        var downUrl =
            "https://softforspeed.51xiazai.cn/alading/NeteaseCloudMusic_Music_official_2.10.6.200601.exe"


        val rootDirPath = getRootDirPath(BaseAppContext.getInstance());
        Globals.log("xxxxxfilesDir", getRootDirPath(BaseAppContext.getInstance()))
        Globals.log("xxxxxfilesDir--本地可用的存储 路劲", filesDir.absolutePath.toString() + "新增的文件名")


//
//        BaseSharePreference.spObject.putString("AppTheme", "light")
//        recreate()


        MainFragmentTabUtils(this, binding.viewPager, binding.radioGroup)

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