package com.xy.demo.base

import android.content.Context
import android.os.Environment



/**
 * author: Leo
 * createDate: 2022/11/22 19:17
 */
class Constants {
//    fAAzohwDUKpfqG0hRqyLHLdlTk+PsJqoiouI01Cmq1lYr5UU6mwSOCcF1wcy75TcI0MDrU0b81tLhyRsN2rjqoLRdrFHoKAf2rbvggUyTEQsDGSW/ePo0Ko8RDf0dzZOQx0TsImMDBHjPqtRLqpp5Xu8EQupEctnVDWJzspirR+Oz+vjzj3PIqscp2NgI+TZSvCR3BNtQRDeZrtBhXNGq6GCGK3EucaGj5gWlioqO1Rf1PUXueOiHVuR7ehB/GqAdD/YuPhEqx6Bu832En/Ye7oIfbikFbIK0pgWAFnd+x5v3ifq3lIBQA==

    companion object {
     
        //域名 上架修改
        const val releaseBaseUrl = "https://toolapi.shenjiwenhua.com/"
        const val debugBaseUrl = "https://toolapi.shenjiwenhua.com/"

 
        const val DB_NAME = "pdf_tool"


        //注册协议
        const val AGREEMENT_PRIVACY = "https://toolm.shenjiwenhua.com/getAgreement/?appkey=100001&type=1"
        
   
    
    
        @kotlin.jvm.JvmField
        val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
        
        //app 私有目录-----缓存图片
        @kotlin.jvm.JvmField
        val xxyDir =  MyApplication.instance.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.path+ "/xxy/"
        
        //手机公共目录
        @kotlin.jvm.JvmField
        val publicXXYDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path+"/"


        //切换正式 测试环境  正式  true    false debug
        const val KEY_APP_RELEASE = "key_app_release"
  
    
        const val BITMAP_LIST = "bitmap_list"
        const val TEXT_LIST = "text_list"
    
     
    
    
        //首次启动App
        const val SHARE_LANGUAGE  = "share_language"
        
        
        //pdf  提取文字切割符文
        const val PDF_TEXT_SPLIT  = "PDF_TEXT_SPLIT"
        
        
        
        //pdf 列表入口
        //1：主页 2 文件页    4 压缩 5 提取文字 6 提取图片  7 加密pdf  8 解锁pdf
        const val PDF_FROM_HOME = 1
        const val PDF_FROM_FILE = 2
        const val PDF_FROM_COMPRESS = 4
        const val PDF_FROM_WORD = 5
        const val PDF_FROM_BITMAP = 6
        const val PDF_FROM_LOCK = 7
        const val PDF_FROM_UNLOCK = 8
        
        
        
        
        //LiveBus
        const val EVENT_REFRESH_FILE = "event_refresh_file"  // 刷新文件列表---  大小更改
        const val EVENT_REFRESH_FILE_EDIT = "event_refresh_file_edit"  // 刷新文件列表---文件名字  删除
        
        
        const val EVENT_REFRESH_FILE_FRAGMENT = "event_refresh_file_fragment"  // 刷新文件列表---
        
        
        
        
        const val  ENCRYPT_FORMAT= "encrypt.pdf"
        const val  COMPRESS_FORMAT= "compress.pdf"
        
        
        

 

//        fun assembleAgreement(context: Context, agreementCheckbox: CheckBox) {
//            val span = SpannableStringBuilder("我已阅读并同意")
//            val agreement = SpannableString("《用户协议》")
//            agreement.setSpan(object : ClickableSpan() {
//                override fun onClick(v: View) {
//                    AgreementActivity.startServer(context)
////                val intent = Intent(this@LoginActivity, AuthWebVeiwActivity::class.java)
////                intent.putExtra("url", Constant.AGREEMENT_SERVICE)
////                intent.putExtra("name", "服务协议")
////                startActivity(intent)
//                }
//
//                override fun updateDrawState(ds: TextPaint) {
//                    super.updateDrawState(ds)
//                    //设置字体颜色
//                    ds.color = ContextCompat.getColor(context, R.color.colorPrimary)
//                    ds.isUnderlineText = false //去掉下划线
//                    ds.bgColor = ContextCompat.getColor(context, R.color.background_color)
//                }
//            }, 0, agreement.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//            span.append(agreement)
//
//            val privacy = SpannableString("《隐私协议》")
//            privacy.setSpan(object : ClickableSpan() {
//                override fun onClick(p0: View) {
//                    AgreementActivity.startPrivacy(context)
////                val intent = Intent(this@LoginActivity, AuthWebVeiwActivity::class.java)
////                intent.putExtra("url", Constant.AGREEMENT_PRIVACY)
////                intent.putExtra("name", "隐私政策")
////                startActivity(intent)
//                }
//
//                override fun updateDrawState(ds: TextPaint) {
//                    super.updateDrawState(ds)
//                    //设置字体颜色
//                    ds.color = ContextCompat.getColor(context, R.color.colorPrimary)
//                    ds.isUnderlineText = false //去掉下划线
//                    ds.bgColor = ContextCompat.getColor(context, R.color.background_color)
//                }
//            }, 0, privacy.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//            span.append(privacy)
//
//            agreementCheckbox.text = span
//            agreementCheckbox.movementMethod = LinkMovementMethod.getInstance()
//        }

    }
}