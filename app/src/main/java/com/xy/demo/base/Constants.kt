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
    
    
        //切换正式 测试环境  正式  true    false debug
        const val KEY_APP_RELEASE = "key_app_release"
        
        //域名 上架修改
        const val releaseBaseUrl = "http://47.251.38.88:8080/"
        const val debugBaseUrl = "http://47.251.38.88:8080/"
        
        
        //注册协议
        const val AGREEMENT_PRIVACY = "http://47.251.38.88:8099/getAgreement/?appkey=100000&type=1"
        const val AGREEMENT_SERVICE = " "
    
    
    
        //接口加密
        const val ZS_AES_KEY = "9eYKK6y18IngV4QA"
        const val ZS_SECRET_KEY = "61bdbc05c842aa07edb84e7e585ec65f"
    
    
        const val NANO_SORT = 59168

 

        //首页广告
        var showMainTopBanner :Boolean = true
        var showMainBottomBanner :Boolean = true
        
        
        //是首页列表 进入遥控器信息 保存界面
        var isHomeToSave = true
 
        
        
        
        

        var FILE_IMG_VIDEO =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .toString()
    
    
    
    
        //key 内部传参
        const val KEY_BRAND_LIST = "BrandListModel"    //存储本地品牌列表
        const val KEY_REMOTE = "remoteModel"
        const val KEY_TV_BRAND = "TV_Brand"
        const val KEY_TV_BRAND_ID = "TV_Brand_id"
        const val KEY_FEEDBACK = "feedback"  //是反馈界面 跳转品牌--子品牌选择
        const val KEY_FILE_TYPE = "key_file_type"//投屏文件类型
        const val KEY_INIT = "key_init"//投
    
    
    
        //eventBus
        const val EVENT_DEVICES = "event_devices"
        const val EVENT_SCROLL_UP = "event_scroll_up"
    
    
    
    
    
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