package com.xy.demo

import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.core.content.ContextCompat
import com.downloader.OnCancelListener
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityMainBinding
import com.xy.demo.network.Globals
import com.xy.demo.network.NetLaunchManager.launchRequest
import com.xy.demo.network.NetManager
import com.xy.demo.ui.SecondActivity
import com.xy.xframework.base.BaseAppContext
import com.xy.xframework.base.XBaseViewModel
import com.xy.xframework.utils.PhoneBookUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : MBBaseActivity<ActivityMainBinding, XBaseViewModel>() {


    var downloadId =0
    override fun getLayoutId(): Int = R.layout.activity_main


    override fun showTitleBar(): Boolean = false

    override fun fitsSystemWindows(): Boolean {
        return false
    }

    override fun initView() {

        launchRequest({NetManager.getStoreCount()},{},{})


        var downUrl ="https://softforspeed.51xiazai.cn/alading/NeteaseCloudMusic_Music_official_2.10.6.200601.exe"


        val rootDirPath = getRootDirPath(BaseAppContext.getInstance());
        Globals.log("xxxxxfilesDir", getRootDirPath(BaseAppContext.getInstance()))
        Globals.log("xxxxxfilesDir--",filesDir.absolutePath.toString())



        binding.cancelTV.setOnClickListener {


            PhoneBookUtils.deleteFuzzy(this@MainActivity,"AA")
//            PRDownloader.cancel(downloadId)
        }

        binding.pauseTV.setOnClickListener {
            PRDownloader.pause(downloadId)
        }

        binding.continueTV.setOnClickListener {
            PRDownloader.resume(downloadId)
        }



        binding.downTV.setOnClickListener {


              downloadId = PRDownloader.download(downUrl, filesDir.absolutePath.toString() , "aaaa")
                .build()
                .setOnStartOrResumeListener {
                    binding.progressA.progress = 20
                }
                .setOnPauseListener {

                }
                .setOnCancelListener(object : OnCancelListener {
                    override fun onCancel() {
                        binding.progressA.progress = 0
                    }
                })
                .setOnProgressListener {

                    val progressPercent: Long = it.currentBytes * 100 / it.totalBytes
                    binding.progressA.setProgress(progressPercent.toInt())

//                binding.progress.progress = progress.progress
                    Globals.log("xxxxxfilesDir-progress.progress-"+ it.currentBytes.toDouble() /it.totalBytes.toDouble() )

                }

                .start(object : OnDownloadListener {
                    override fun onDownloadComplete() {
                        Globals.log("xxxxxonDownloadComplete"  )



                    }
                    override fun onError(error: com.downloader.Error?) {

                    }


                })

        }


        binding.nextTV.setOnClickListener {

            startActivity(Intent(this@MainActivity,SecondActivity::class.java))
        }


    }


    override fun onResume() {
        super.onResume()
        PRDownloader.resume(downloadId)
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