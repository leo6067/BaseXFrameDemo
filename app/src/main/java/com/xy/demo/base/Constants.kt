package com.xy.demo.base

import android.content.Context
import android.os.Environment
import com.xy.xframework.base.BaseAppContext


/**
 * author: Leo
 * createDate: 2022/11/22 19:17
 */
class Constants {
//    fAAzohwDUKpfqG0hRqyLHLdlTk+PsJqoiouI01Cmq1lYr5UU6mwSOCcF1wcy75TcI0MDrU0b81tLhyRsN2rjqoLRdrFHoKAf2rbvggUyTEQsDGSW/ePo0Ko8RDf0dzZOQx0TsImMDBHjPqtRLqpp5Xu8EQupEctnVDWJzspirR+Oz+vjzj3PIqscp2NgI+TZSvCR3BNtQRDeZrtBhXNGq6GCGK3EucaGj5gWlioqO1Rf1PUXueOiHVuR7ehB/GqAdD/YuPhEqx6Bu832En/Ye7oIfbikFbIK0pgWAFnd+x5v3ifq3lIBQA==



//    var downUrl =
//        "https://softforspeed.51xiazai.cn/alading/NeteaseCloudMusic_Music_official_2.10.6.200601.exe"
//
//    val rootDirPath = getRootDirPath(BaseAppContext.getInstance());
//    Globals.log("xxxxxfilesDir", getRootDirPath(BaseAppContext.getInstance()))
//    Globals.log("xxxxxfilesDir--本地可用的存储 路劲", filesDir.absolutePath.toString() + "新增的文件名")
    
    
    companion object {
    
    
    
        //域名 上架修改
        const val releaseBaseUrl = "https://toolapi.shenjiwenhua.com/"
        const val debugBaseUrl = "https://toolapi.shenjiwenhua.com/"
//        const val debugBaseUrl = "http://47.251.38.88:8080/"
    
    
        //注册协议
        const val AGREEMENT_PRIVACY = "https://toolm.shenjiwenhua.com/getAgreement/?appkey=100001&type=1"
    
        //本地设备数据库
        const val DB_NAME = "sjykq_db"
    
     
    
        //BaseSharePreference key
        //首次启动App
        const val SHARE_FIRST  = "share_first"
        const val SHARE_LANGUAGE  = "share_language"
        
        


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